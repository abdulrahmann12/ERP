package com.learn.erp.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "purchases")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Purchase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long purchaseId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "supplier_id", nullable = false)
    private Supplier supplier;

    private BigDecimal totalAmount;

    private LocalDateTime date;

    @Column(columnDefinition = "TEXT")
    private String notes;

    @OneToMany(mappedBy = "purchase", cascade = CascadeType.ALL, orphanRemoval =  true)
    @Builder.Default
    private List<PurchaseItem> items = new ArrayList<>();
}
