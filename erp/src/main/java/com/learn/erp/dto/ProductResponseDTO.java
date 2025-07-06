package com.learn.erp.dto;

import lombok.Data;

import java.math.BigDecimal;

import com.learn.erp.model.Product.Unit;

@Data
public class ProductResponseDTO {
    private Long productId;
    private String name;
    private String code;
    private String description;
    private BigDecimal price;
    private Integer stock;
    private Unit unit;
    private String categoryName;
}
