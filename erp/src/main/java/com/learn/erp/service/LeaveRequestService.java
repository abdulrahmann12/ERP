package com.learn.erp.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.learn.erp.config.Messages;
import com.learn.erp.dto.LeaveRequestCreateDTO;
import com.learn.erp.dto.LeaveRequestResponseDTO;
import com.learn.erp.dto.LeaveRequestStatusUpdateDTO;
import com.learn.erp.dto.UserLeaveRequestResponseDTO;
import com.learn.erp.exception.DuplicateResourceException;
import com.learn.erp.exception.LeaveRequestNotFoundException;
import com.learn.erp.exception.UserNotFoundException;
import com.learn.erp.mapper.LeaveRequestMapper;
import com.learn.erp.model.LeaveRequest;
import com.learn.erp.model.LeaveRequest.Status;
import com.learn.erp.model.User;
import com.learn.erp.repository.LeaveRequestRepository;
import com.learn.erp.repository.UserRepository;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Service
@Validated
@RequiredArgsConstructor
public class LeaveRequestService {
	
	private final LeaveRequestRepository leaveRequestRepository;
	private final LeaveRequestMapper leaveRequestMapper;
	private final UserRepository userRepository;
	
	public UserLeaveRequestResponseDTO createLeaveRequest(Long userId, @Valid LeaveRequestCreateDTO dto) {
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new UserNotFoundException());
		List<LeaveRequest> overlappingRequests = leaveRequestRepository
		        .findByUser_IdAndStatus(userId, Status.PENDING)
		        .stream()
		        .filter(r ->
		            !(dto.getEndDate().isBefore(r.getStartDate()) || dto.getStartDate().isAfter(r.getEndDate()))
		        )
		        .toList();

	    if (!overlappingRequests.isEmpty()) {
	        throw new DuplicateResourceException(Messages.USER_HAVE_PEMDING_REQUEST);
	    }
	    
		LeaveRequest request = leaveRequestMapper.toEntity(dto);
		request.setUser(user);
		request.setStatus(Status.PENDING);
		
		leaveRequestRepository.save(request);
		return leaveRequestMapper.toUserDTO(request);
	}
	
	public Page<LeaveRequestResponseDTO> getAllLeaveRequests(int page, int size){
		Pageable pageable = PageRequest.of(page, size);
		return leaveRequestRepository.findAll(pageable).map(leaveRequestMapper::toHRDTO);
	}

	public Page<LeaveRequestResponseDTO> HRGetLeaveRequestsByUser(Long userId, int page, int size){
		Pageable pageable = PageRequest.of(page, size);
		return leaveRequestRepository.findByUser_Id(userId, pageable).map(leaveRequestMapper::toHRDTO);
	}
	
	public Page<UserLeaveRequestResponseDTO> getLeaveRequestsByUser(Long userId, int page, int size){
		Pageable pageable = PageRequest.of(page, size);
		return leaveRequestRepository.findByUser_Id(userId, pageable).map(leaveRequestMapper::toUserDTO);
	}

    public Page<LeaveRequestResponseDTO> getLeaveRequestsByStatus(Status status, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return leaveRequestRepository.findByStatus(status, pageable)
                .map(leaveRequestMapper::toHRDTO);
    }
    
    public void updateLeaveRequestStatus(Long requestId, @Valid LeaveRequestStatusUpdateDTO dto) {
        LeaveRequest request = leaveRequestRepository.findById(requestId)
            .orElseThrow(() -> new LeaveRequestNotFoundException());
        
        request.setStatus(dto.getStatus());
        leaveRequestRepository.save(request);
    }
}
