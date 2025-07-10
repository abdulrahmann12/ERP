package com.learn.erp.repository;


import com.learn.erp.model.Attendance;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Long> {

	Page<Attendance> findAllByDate(LocalDate date, Pageable pageable);

    Page<Attendance> findAllByUser_Id(Long userId, Pageable pageable);
    List<Attendance> findByDateAndCheckOutIsNull(LocalDate date);
    Optional<Attendance> findByUser_IdAndDate(Long userId, LocalDate date);
}
