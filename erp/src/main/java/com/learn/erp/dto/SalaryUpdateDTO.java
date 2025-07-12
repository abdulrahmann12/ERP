package com.learn.erp.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class SalaryUpdateDTO {
	
    @NotNull(message = "Basic salary is required")
    @Positive(message = "Salary must be greater than zero")
    private BigDecimal basicSalary;
}