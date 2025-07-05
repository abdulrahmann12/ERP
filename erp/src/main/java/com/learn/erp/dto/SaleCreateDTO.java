package com.learn.erp.dto;

import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SaleCreateDTO {

    @NotNull(message = "Customer ID is required")
    private Long customerId;

    @NotNull(message = "Notes is required")
    private String notes;

    private List<@Valid SaleItemCreateDTO> items;
}
