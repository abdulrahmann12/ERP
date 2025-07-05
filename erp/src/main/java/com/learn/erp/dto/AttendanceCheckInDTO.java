package com.learn.erp.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class AttendanceCheckInDTO {

    @NotNull(message = "User ID is required")
    private Long userId;

    @NotNull(message = "Date is required")
    private LocalDate date;

    @NotNull(message = "Check-in time is required")
    private LocalTime checkIn;
}