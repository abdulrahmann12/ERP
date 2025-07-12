package com.learn.erp.dto;


import lombok.Data;

import java.math.BigDecimal;

@Data
public class SalaryResponseDTO {

    private Long salaryId;
    private HRViewUserResponseDTO user;
    private BigDecimal basicSalary;
}