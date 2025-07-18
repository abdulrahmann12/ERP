package com.learn.erp.controller;

import java.time.LocalDate;

import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.learn.erp.config.Messages;
import com.learn.erp.dto.AttendanceResponseDTO;
import com.learn.erp.dto.BasicResponse;
import com.learn.erp.model.User;
import com.learn.erp.service.AttendanceService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(
	    name = "Attendance Controller",
	    description = "API for managing employee attendance (check-in, check-out, and attendance history)."
	)
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/attendance")
public class AttendanceController {

	private final AttendanceService attendanceService;

	@Operation(
			summary = "Employee check-in",
			description = "Allows an authenticated employee to check in for the work day."
	)
	@PostMapping("/check-in")
	@PreAuthorize("isAuthenticated()")
	public ResponseEntity<BasicResponse> checkIn(@AuthenticationPrincipal User user){
		Long userId = user.getId();
		AttendanceResponseDTO response = attendanceService.checkIn(userId);
		return ResponseEntity.ok(new BasicResponse(Messages.CHECK_IN , response));
	}
	
    @Operation(
            summary = "Employee check-out",
            description = "Allows an authenticated employee to check out from work."
    )
	@PutMapping("/check-out")
	@PreAuthorize("isAuthenticated()")
	public ResponseEntity<BasicResponse> checkOut(@AuthenticationPrincipal User user){
		Long userId = user.getId();
		AttendanceResponseDTO response = attendanceService.checkOut(userId);
		return ResponseEntity.ok(new BasicResponse(Messages.CHECK_OUT , response));
	}
	
    @Operation(
            summary = "Get user attendance history",
            description = "Retrieve the full attendance history for a specific user by their ID. Only HR and Admin can access."
    )
    @GetMapping("/user/{userId}")
    @PreAuthorize("hasAnyRole('HR', 'ADMIN')")
    public ResponseEntity<Page<AttendanceResponseDTO>> getUserHistory(
            @PathVariable Long userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<AttendanceResponseDTO> history = attendanceService.getUserAttendanceHistory(userId, page, size);
        return ResponseEntity.ok(history);
    }
    
    @Operation(
            summary = "Get all attendance records",
            description = "Retrieve all attendance records in the system. Only accessible by HR."
    )
    @GetMapping
    @PreAuthorize("hasRole('HR')")
    public ResponseEntity<Page<AttendanceResponseDTO>> getAllAttendance(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<AttendanceResponseDTO> all = attendanceService.getAllAttendance(page, size);
        return ResponseEntity.ok(all);
    }
    
    @Operation(
            summary = "Get attendance by date",
            description = "Retrieve all attendance records for a specific date. Only accessible by HR."
    )
    @GetMapping("/by-date")
    @PreAuthorize("hasRole('HR')")
    public ResponseEntity<Page<AttendanceResponseDTO>> getAttendanceByDate(
            @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<AttendanceResponseDTO> attendance = attendanceService.getAttendanceByDate(date, page, size);
        return ResponseEntity.ok(attendance);
    }
}