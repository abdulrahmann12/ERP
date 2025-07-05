package com.learn.erp.dto;

import com.learn.erp.model.EmployeeDetails.ContractType;
import lombok.Data;

import java.time.LocalDate;

@Data
public class EmployeeDetailsResponseDTO {
    private Long employeeId;
    private Long userId;
    private String nationalId;
    private LocalDate hireDate;
    private String jobTitle;
    private String emergencyContact;
    private String address;
    private ContractType contractType;
}