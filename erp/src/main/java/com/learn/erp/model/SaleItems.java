package com.learn.erp.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "sale_items")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SaleItems {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long saleItemId;

    @ManyToOne(optional = false)
    @JoinColumn(name = "sale_id", nullable = false)
    private Sale sale;

    @ManyToOne(optional = false)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    private Integer quantity;

    private BigDecimal price;

}
