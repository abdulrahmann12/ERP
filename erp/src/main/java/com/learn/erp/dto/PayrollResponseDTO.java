package com.learn.erp.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class PayrollResponseDTO {

    private Long payrollId;
    private UserSummaryDTO user;
    private int month;
    private int year;
    private BigDecimal basicSalary;
    private BigDecimal bonuses;
    private BigDecimal deductions;
    private BigDecimal netSalary;
    private LocalDateTime createdAt;
}