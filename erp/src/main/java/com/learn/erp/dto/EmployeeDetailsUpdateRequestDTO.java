package com.learn.erp.dto;

import com.learn.erp.model.EmployeeDetails.ContractType;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;

@Data
public class EmployeeDetailsUpdateRequestDTO {

    private Long employeeId;
    
    @NotNull(message = "User ID is required")
    private Long userId;
	
    @NotBlank(message = "National ID is required")
    @Size(min = 14, max = 14, message = "National ID must be exactly 14 digits")
    private String nationalId;

    @NotNull(message = "Hire date is required")
    @PastOrPresent(message = "Hire date cannot be in the future")
    private LocalDate hireDate;

    @NotBlank(message = "Job title is required")
    private String jobTitle;

    @NotBlank(message = "Emergency contact is required")
    private String emergencyContact;

    @NotBlank(message = "Address is required")
    private String address;

    @NotNull(message = "Contract type is required")
    private ContractType contractType;
}
