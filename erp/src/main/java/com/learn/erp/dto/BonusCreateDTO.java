package com.learn.erp.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class BonusCreateDTO {
	
	@NotNull(message = "User ID is required")
    private Long userId;
	
    @NotNull(message = "Amount is required")
    @Positive(message = "Amount must be a positive number")
    private BigDecimal amount;
    
    @NotBlank(message = "Reason is required")
    @Size(max = 255, message = "Reason must not exceed 255 characters")
    private String reason;
    
    @Min(value = 1, message = "Month must be between 1 and 12")
    @Max(value = 12, message = "Month must be between 1 and 12")
    private int month;
    
    @Min(value = 2000, message = "Year must be a valid year")
    private int year;
}
