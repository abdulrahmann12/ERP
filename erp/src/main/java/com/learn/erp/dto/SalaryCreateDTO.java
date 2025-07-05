package com.learn.erp.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class SalaryCreateDTO {

    @NotNull(message = "User ID is required")
    private Long userId;

    @NotNull(message = "Basic salary is required")
    @Positive(message = "Salary must be greater than zero")
    private BigDecimal basicSalary;
}