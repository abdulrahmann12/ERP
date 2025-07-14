package com.learn.erp.dto;

import java.math.BigDecimal;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SupplierPurchaseReportDTO {

    private SupplierDTO supplier;

    private BigDecimal totalSales;

    private int numberOfOrders;
}
