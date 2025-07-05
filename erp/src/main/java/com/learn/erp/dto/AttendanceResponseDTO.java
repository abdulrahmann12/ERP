package com.learn.erp.dto;

import com.learn.erp.model.Attendance.Status;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class AttendanceResponseDTO {

    private Long attendanceId;
    private UserSummaryDTO user;
    private String userFullName;
    private LocalDate date;
    private LocalTime checkIn;
    private LocalTime checkOut;
    private int workingHours;
    private Status status;
}
