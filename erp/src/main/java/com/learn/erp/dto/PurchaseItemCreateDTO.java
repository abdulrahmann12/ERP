package com.learn.erp.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;


@Data
public class PurchaseItemCreateDTO {

    @NotNull(message = "ProductId is required")
    private Long productId;

    @Positive(message = "Quantity must be positive")
    private Integer quantity;

}
