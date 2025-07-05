package com.learn.erp.dto;


import java.math.BigDecimal;

import lombok.Data;

@Data
public class ProductSummaryDTO {
    private Long productId;
    private String name;
    private String code;
    private String description;
    private BigDecimal price;
    private String unit;
    private String categoryName;
}
