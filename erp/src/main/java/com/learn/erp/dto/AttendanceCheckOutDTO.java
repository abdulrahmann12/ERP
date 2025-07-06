package com.learn.erp.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalTime;

@Data
public class AttendanceCheckOutDTO {

    @NotNull(message = "Attendance Id is required")
	private Long attendanceId;

    @NotNull(message = "Check-out time is required")
    private LocalTime checkOut;
}