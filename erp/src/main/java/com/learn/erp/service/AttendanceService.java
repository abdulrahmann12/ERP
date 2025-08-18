package com.learn.erp.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.validation.annotation.Validated;

import com.learn.erp.config.AfterCommitExecutor;
import com.learn.erp.config.Messages;
import com.learn.erp.dto.AttendanceResponseDTO;
import com.learn.erp.exception.AlreadyCheckedOutException;
import com.learn.erp.exception.DuplicateResourceException;
import com.learn.erp.exception.MailSendingException;
import com.learn.erp.exception.UserNotFoundException;
import com.learn.erp.mapper.AttendanceMapper;
import com.learn.erp.model.Attendance;
import com.learn.erp.model.Attendance.Status;
import com.learn.erp.model.User;
import com.learn.erp.repository.AttendanceRepository;
import com.learn.erp.repository.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Validated
@RequiredArgsConstructor
public class AttendanceService {

	private final AttendanceRepository attendanceRepository;
	private final UserRepository userRepository;
	private final AttendanceMapper attendanceMapper;
	private final EmailService emailService;

	@Transactional
	public AttendanceResponseDTO checkIn(Long userId) {
		User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException());

		LocalDate today = LocalDate.now();
		Optional<Attendance> existingAttendance = attendanceRepository.findByUser_IdAndDate(userId, today);

		if (existingAttendance.isPresent()) {
			throw new DuplicateResourceException(Messages.USER_ALREADY_CHECKIN);
		}
		LocalTime now = LocalTime.now();
		Status status = now.isAfter(LocalTime.of(9, 0)) ? Status.LATE : Status.PRESENT;

		Attendance newAttendance = Attendance.builder().user(user).checkIn(now).status(status).build();

		attendanceRepository.save(newAttendance);
		return attendanceMapper.toDTO(newAttendance);
	}

	@Transactional
	public AttendanceResponseDTO checkOut(Long userId) {
		userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException());
		Attendance attendance = attendanceRepository.findByUser_IdAndDate(userId, LocalDate.now())
				.orElseThrow(() -> new RuntimeException(Messages.MUST_CHECKIN));
		if (attendance.getCheckOut() != null) {
			throw new AlreadyCheckedOutException();
		}
		LocalTime now = LocalTime.now();
		attendance.setCheckOut(now);

		int workedMinutes = java.time.Duration.between(attendance.getCheckIn(), now).toMinutesPart();
		int workedHours = java.time.Duration.between(attendance.getCheckIn(), now).toHoursPart();
		attendance.setWorkingHours((workedHours * 60 + workedMinutes) / 60);

		if (attendance.getStatus() != Status.LATE) {
			if (workedHours < 8) {
				attendance.setStatus(Status.LATE);
			} else {
				attendance.setStatus(Status.PRESENT);
			}
		}
		attendanceRepository.save(attendance);
		return attendanceMapper.toDTO(attendance);
	}

	@Cacheable(value = "userAttendanceHistory", key = "#userId + '_' + #page + '_' + #size")
	public Page<AttendanceResponseDTO> getUserAttendanceHistory(Long userId, int page, int size) {
		userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException());
		Pageable pageable = PageRequest.of(page, size);
		Page<Attendance> attendances = attendanceRepository.findAllByUser_Id(userId, pageable);
		return attendances.map(attendanceMapper::toDTO);
	}

	
	public Page<AttendanceResponseDTO> getAllAttendance(int page, int size) {
		Pageable pageable = PageRequest.of(page, size);
		Page<Attendance> attendances = attendanceRepository.findAll(pageable);
		return attendances.map(attendanceMapper::toDTO);
	}

	@Cacheable(value = "attendanceByDate", key = "#date")
	public Page<AttendanceResponseDTO> getAttendanceByDate(LocalDate date, int page, int size) {
		Pageable pageable = PageRequest.of(page, size);
		Page<Attendance> attendances = attendanceRepository.findAllByDate(date, pageable);
		return attendances.map(attendanceMapper::toDTO);
	}

	@Transactional
	@Scheduled(cron = "0 0 17 * * *")
	public void autoCheckOutMissingAttendances() {

		LocalDate today = LocalDate.now();
		List<Attendance> attendances = attendanceRepository.findByDateAndCheckOutIsNull(today);

		for (Attendance att : attendances) {
			LocalTime autoCheckoutTime = LocalTime.of(17, 0);
			att.setCheckOut(autoCheckoutTime);
			int workedMinutes = java.time.Duration.between(att.getCheckIn(), autoCheckoutTime).toMinutesPart();
			int workedHours = java.time.Duration.between(att.getCheckIn(), autoCheckoutTime).toHoursPart();
			att.setWorkingHours((workedHours * 60 + workedMinutes) / 60);
			att.setStatus(Status.AUTO_CHECKED_OUT);
		}
		attendanceRepository.saveAll(attendances);
	}

	@Transactional
	@Scheduled(cron = "0 59 23 * * *")
	public void markAbsencesAndNotify() {
		LocalDate today = LocalDate.now();

		List<User> allUsers = userRepository.findAll();
		List<Attendance> todaysAttendances = attendanceRepository.findByDate(today);
		List<Long> attendedUserIds = todaysAttendances.stream().map(a -> a.getUser().getId()).toList();

		List<Attendance> absences = new ArrayList<>();

		for (User user : allUsers) {
			if (!attendedUserIds.contains(user.getId())) {
				Attendance absent = Attendance.builder().user(user).date(today).status(Status.ABSENT).checkIn(null)
						.checkOut(null).workingHours(0).build();

				absences.add(absent);

				TransactionSynchronizationManager.registerSynchronization(new AfterCommitExecutor() {
					@Override
					public void afterCommit() {
						try {
							emailService.sendAbsenceAlert(user);
						} catch (Exception e) {
							throw new MailSendingException();
						}
					}
				});

			}
		}
		attendanceRepository.saveAll(absences);
	}
}