package com.learn.erp.dto;

import lombok.Data;

@Data
public class CategoryResponseDTO {
    private Long categoryId;
    private String name;
    private String description;
    private String userFullName;
}