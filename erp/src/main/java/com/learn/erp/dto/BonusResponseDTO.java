package com.learn.erp.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class BonusResponseDTO {
    private Long bonusId;
    private BigDecimal amount;
    private String reason;
    private int month;
    private int year;
    private String userFullName;
}