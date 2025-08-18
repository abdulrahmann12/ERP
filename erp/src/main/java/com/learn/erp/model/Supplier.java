package com.learn.erp.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "suppliers",indexes = { @Index(name = "idx_supplier_email", columnList = "email", unique = true) })
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Supplier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long supplierId;

    private String name;

    private String phone;

    private String email;

    private String address;
}
