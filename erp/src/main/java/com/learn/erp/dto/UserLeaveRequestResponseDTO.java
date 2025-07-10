package com.learn.erp.dto;

import com.learn.erp.model.LeaveRequest.Status;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class UserLeaveRequestResponseDTO {

    private Long requestId;
    private LocalDate startDate;
    private LocalDate endDate;
    private String reason;
    private Status status;
    private LocalDateTime createdAt;
}	
