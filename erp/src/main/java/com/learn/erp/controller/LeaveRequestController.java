package com.learn.erp.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import com.learn.erp.config.Messages;
import com.learn.erp.dto.BasicResponse;
import com.learn.erp.dto.LeaveRequestCreateDTO;
import com.learn.erp.dto.LeaveRequestResponseDTO;
import com.learn.erp.dto.LeaveRequestStatusUpdateDTO;
import com.learn.erp.dto.UserLeaveRequestResponseDTO;
import com.learn.erp.model.LeaveRequest.Status;
import com.learn.erp.model.User;
import com.learn.erp.service.LeaveRequestService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/leave-requests")
@RequiredArgsConstructor
public class LeaveRequestController {
	private final LeaveRequestService leaveRequestService;
	

    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<BasicResponse> createLeaveRequest(@AuthenticationPrincipal User user, @RequestBody LeaveRequestCreateDTO dto) {
    	UserLeaveRequestResponseDTO response = leaveRequestService.createLeaveRequest(user.getId(), dto);
		return ResponseEntity.ok(new BasicResponse(Messages.LEAVE_REQUEST_SUBMITTED, response));
    }
    
    @GetMapping("/my-requests")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Page<UserLeaveRequestResponseDTO>> getMyRequests(@AuthenticationPrincipal User user,
                                                                           @RequestParam(defaultValue = "0") int page,
                                                                           @RequestParam(defaultValue = "10") int size) {
        Page<UserLeaveRequestResponseDTO> response = leaveRequestService.getLeaveRequestsByUser(user.getId(), page, size);
        return ResponseEntity.ok(response);
    }
    
    @GetMapping
    @PreAuthorize("hasRole('HR')")
    public ResponseEntity<Page<LeaveRequestResponseDTO>> getAllRequests(@RequestParam(defaultValue = "0") int page,
                                                                         @RequestParam(defaultValue = "10") int size) {
        Page<LeaveRequestResponseDTO> response = leaveRequestService.getAllLeaveRequests(page, size);
        return ResponseEntity.ok(response);
    }	
    
    @GetMapping("/user/{userId}")
    @PreAuthorize("hasRole('HR')")
    public ResponseEntity<Page<LeaveRequestResponseDTO>> getRequestsByUser(@PathVariable Long userId,
                                                                           @RequestParam(defaultValue = "0") int page,
                                                                           @RequestParam(defaultValue = "10") int size) {
        Page<LeaveRequestResponseDTO> response = leaveRequestService.HRGetLeaveRequestsByUser(userId, page, size);
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/status")
    @PreAuthorize("hasRole('HR')")
    public ResponseEntity<Page<LeaveRequestResponseDTO>> getRequestsByStatus(@RequestParam Status status,
                                                                             @RequestParam(defaultValue = "0") int page,
                                                                             @RequestParam(defaultValue = "10") int size) {
        Page<LeaveRequestResponseDTO> response = leaveRequestService.getLeaveRequestsByStatus(status, page, size);
        return ResponseEntity.ok(response);
    }
    
    @PutMapping("/{requestId}/status")
    @PreAuthorize("hasRole('HR')")
    public ResponseEntity<BasicResponse> updateStatus(@PathVariable Long requestId,
                                                      @RequestBody LeaveRequestStatusUpdateDTO dto) {
        leaveRequestService.updateLeaveRequestStatus(requestId, dto);
        return ResponseEntity.ok(new BasicResponse(Messages.LEAVE_REQUEST_STATUS_UPDATED));
    }
}
