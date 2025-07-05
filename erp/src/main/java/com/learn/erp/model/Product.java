package com.learn.erp.model;


import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "products")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;

    private String name;

    private String code;

    private String description;

    private BigDecimal price;

    private Integer stock;

    @Enumerated(EnumType.STRING)
    private Unit unit;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;
    
    public enum Unit {
        PIECE,
        KILOGRAM,
        LITER,
        BOX,
        METER,
        DOZEN
    }
}
