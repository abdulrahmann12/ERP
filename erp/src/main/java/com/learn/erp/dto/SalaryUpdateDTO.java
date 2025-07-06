package com.learn.erp.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class SalaryUpdateDTO {

    @NotNull(message = "Salary ID is required")
    private Long salaryId;
	
    @NotNull(message = "User ID is required")
    private Long userId;

    @NotNull(message = "Basic salary is required")
    @Positive(message = "Salary must be greater than zero")
    private BigDecimal basicSalary;
}