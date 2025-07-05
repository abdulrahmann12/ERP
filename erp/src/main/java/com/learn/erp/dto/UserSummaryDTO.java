package com.learn.erp.dto;

import lombok.Data;

@Data
public class UserSummaryDTO {
    private Long userId;
    private String username;
    private String fullName;
    private String phone;
    private String department;
}