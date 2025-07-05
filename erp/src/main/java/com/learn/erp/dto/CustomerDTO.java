package com.learn.erp.dto;

import lombok.Data;

@Data
public class CustomerDTO {
    private Long customerId;
    private String name;
    private String phone;
    private String email;
    private String address;
}