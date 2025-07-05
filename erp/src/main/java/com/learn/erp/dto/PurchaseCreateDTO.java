package com.learn.erp.dto;


import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class PurchaseCreateDTO {

    @NotNull(message = "Supplier ID is required")
    private Long supplierId;

    @NotNull(message = "Notes is required")
    private String notes;

    private List<@Valid PurchaseItemCreateDTO> items;
}