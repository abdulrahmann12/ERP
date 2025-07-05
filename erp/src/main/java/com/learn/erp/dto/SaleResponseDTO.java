package com.learn.erp.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class SaleResponseDTO {

    private Long saleId;

    private UserSummaryDTO user;

    private CustomerDTO customer;

    private BigDecimal totalAmount;

    private String notes;

    private LocalDateTime date;

    private List<SaleItemResponseDTO> items;
}
