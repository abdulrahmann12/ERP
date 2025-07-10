package com.learn.erp.dto;

import com.learn.erp.model.LeaveRequest.Status;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LeaveRequestStatusUpdateDTO {
	
    @NotNull(message = "Status is required")
    private Status status;
}