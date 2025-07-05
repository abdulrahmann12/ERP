package com.learn.erp.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class PurchaseItemResponseDTO {

    private Long purchaseItemId;

    private ProductSummaryDTO product;

    private Integer quantity;

    private BigDecimal price;

}