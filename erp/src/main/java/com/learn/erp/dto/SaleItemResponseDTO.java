package com.learn.erp.dto;


import lombok.Data;

import java.math.BigDecimal;

@Data
public class SaleItemResponseDTO {

    private Long saleItemId;

    private ProductResponseDTO product;

    private Integer quantity;

    private BigDecimal price;
}
