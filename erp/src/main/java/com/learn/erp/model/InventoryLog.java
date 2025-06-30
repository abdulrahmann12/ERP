package com.learn.erp.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "inventory_logs")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InventoryLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long logId;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    private Integer quantityBefore;

    private Integer quantityAfter;


    @Enumerated(EnumType.STRING)
    private ActionType actionType;

    @Column(columnDefinition = "TEXT")
    private String note;

    private LocalDateTime createdAt;

    public enum ActionType {
        PURCHASE,
        SALE,
        ADJUSTMENT,
        RETURN,
        INVENTORY_CORRECTION
    }
}
