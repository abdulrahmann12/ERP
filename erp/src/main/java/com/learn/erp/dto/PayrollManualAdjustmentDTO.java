package com.learn.erp.dto;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class PayrollManualAdjustmentDTO {

    @NotNull(message = "Payroll ID is required")
    private Long payrollId;

    @Positive(message = "Bonuses must be greater than 0")
    private BigDecimal bonuses;

    @Positive(message = "Deductions must be greater than 0")
    private BigDecimal deductions;
}
