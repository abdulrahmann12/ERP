package com.learn.erp.dto;

import lombok.Data;

@Data
public class SupplierDTO {
    private Long supplierId;
    private String name;
    private String phone;
    private String email;
    private String address;
}
