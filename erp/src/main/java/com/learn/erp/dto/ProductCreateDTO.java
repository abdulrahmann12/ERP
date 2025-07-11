package com.learn.erp.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;

import com.learn.erp.model.Product.Unit;

@Data
public class ProductCreateDTO {

    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Code is required")
    private String code;
    
    @NotBlank(message = "Description is required")
    private String description;

    @NotNull(message = "Price is required")
    @Positive(message = "Price must be positive")
    private BigDecimal price;

    @NotNull(message = "Stock is required")
    @Min(value = 0, message = "Stock cannot be negative")
    private Integer stock;

    @NotNull(message = "Unit is required")
    private Unit unit;

    @NotNull(message = "Category ID is required")
    private Long categoryId;
}
