package com.learn.erp.dto;


import lombok.Data;

import java.math.BigDecimal;

@Data
public class SaleItemResponseDTO {

    private Long saleItemId;

    private ProductSummaryDTO product;

    private Integer quantity;

    private BigDecimal price;
}
