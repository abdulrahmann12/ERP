package com.learn.erp.dto;

import java.math.BigDecimal;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CustomerSalesReportDTO {

    private CustomerDTO customer;

    private BigDecimal totalSales;

    private int numberOfOrders;
}
