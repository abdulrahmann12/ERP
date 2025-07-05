package com.learn.erp.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class DepartmentResponseDTO {
    private Long departmentId;
    private String name;
    private String description;
    private LocalDateTime createdAt;
}