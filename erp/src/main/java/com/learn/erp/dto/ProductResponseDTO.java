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
    private String userFullName;
    
    public ProductResponseDTO(Long productId, String name, String code, String description,
            BigDecimal price, Integer stock, Unit unit,
            String categoryName, String userFullName) {
			this.productId = productId;
			this.name = name;
			this.code = code;
			this.description = description;
			this.price = price;
			this.stock = stock;
			this.unit = unit;
			this.categoryName = categoryName;
			this.userFullName = userFullName;
}
}
