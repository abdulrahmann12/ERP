package com.learn.erp.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class PurchaseResponseDTO {

    private Long purchaseId;

    private UserSummaryDTO user;

    private SupplierDTO supplier;

    private BigDecimal totalAmount;

    private String notes;

    private LocalDateTime date;

    private List<PurchaseItemResponseDTO> items;
}
