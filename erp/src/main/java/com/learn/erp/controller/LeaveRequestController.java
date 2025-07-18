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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(
	    name = "Leave Request Controller",
	    description = "API for submitting and managing employee leave requests (by user or HR)."
	)
@RestController
@RequestMapping("/api/leave-requests")
@RequiredArgsConstructor
public class LeaveRequestController {
	private final LeaveRequestService leaveRequestService;
	
	@Operation(
			summary = "Submit leave request",
			description = "Submit a new leave request by an employee.", 
			tags = {"Leave Request"}
	)
    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<BasicResponse> createLeaveRequest(@AuthenticationPrincipal User user, @RequestBody LeaveRequestCreateDTO dto) {
    	UserLeaveRequestResponseDTO response = leaveRequestService.createLeaveRequest(user.getId(), dto);
		return ResponseEntity.ok(new BasicResponse(Messages.LEAVE_REQUEST_SUBMITTED, response));
    }
    
	@Operation(
			summary = "Get my leave requests",
			description = "Retrieves the current user's submitted leave requests.",
			tags = {"Leave Request"}
	)
    @GetMapping("/my-requests")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Page<UserLeaveRequestResponseDTO>> getMyRequests(@AuthenticationPrincipal User user,
                                                                           @RequestParam(defaultValue = "0") int page,
                                                                           @RequestParam(defaultValue = "10") int size) {
        Page<UserLeaveRequestResponseDTO> response = leaveRequestService.getLeaveRequestsByUser(user.getId(), page, size);
        return ResponseEntity.ok(response);
    }
    
	@Operation(
			summary = "Get all leave requests",
			description = "Retrieves a paginated list of all leave requests submitted by employees.",
			tags = {"Leave Request"}
	)
    @GetMapping
    @PreAuthorize("hasRole('HR')")
    public ResponseEntity<Page<LeaveRequestResponseDTO>> getAllRequests(@RequestParam(defaultValue = "0") int page,
                                                                         @RequestParam(defaultValue = "10") int size) {
        Page<LeaveRequestResponseDTO> response = leaveRequestService.getAllPendingRequests(page, size);
        return ResponseEntity.ok(response);
    }	
    
	@Operation(
			summary = "Get leave requests by user",
			description = "Fetches leave requests submitted by a specific user.", 
			tags = {"Leave Request"}
	)
    @GetMapping("/user/{userId}")
    @PreAuthorize("hasRole('HR')")
    public ResponseEntity<Page<LeaveRequestResponseDTO>> getRequestsByUser(@PathVariable Long userId,
                                                                           @RequestParam(defaultValue = "0") int page,
                                                                           @RequestParam(defaultValue = "10") int size) {
        Page<LeaveRequestResponseDTO> response = leaveRequestService.HRGetLeaveRequestsByUser(userId, page, size);
        return ResponseEntity.ok(response);
    }
    
	@Operation(
			summary = "Get leave requests by status",
			description = "Fetches leave requests filtered by request status (e.g., PENDING, APPROVED, REJECTED).",
			tags = {"Leave Request"}
	)
    @GetMapping("/status")
    @PreAuthorize("hasRole('HR')")
    public ResponseEntity<Page<LeaveRequestResponseDTO>> getRequestsByStatus(@RequestParam Status status,
                                                                             @RequestParam(defaultValue = "0") int page,
                                                                             @RequestParam(defaultValue = "10") int size) {
        Page<LeaveRequestResponseDTO> response = leaveRequestService.getLeaveRequestsByStatus(status, page, size);
        return ResponseEntity.ok(response);
    }
    
	@Operation(
			summary = "Update leave request status",
			description = "Updates the status of a leave request (approve, reject, etc).",
			tags = {"Leave Request"}
	)
    @PutMapping("/{requestId}/status")
    @PreAuthorize("hasRole('HR')")
    public ResponseEntity<BasicResponse> updateStatus(@PathVariable Long requestId,
                                                      @RequestBody LeaveRequestStatusUpdateDTO dto) {
        leaveRequestService.updateLeaveRequestStatus(requestId, dto);
        return ResponseEntity.ok(new BasicResponse(Messages.LEAVE_REQUEST_STATUS_UPDATED));
    }
}