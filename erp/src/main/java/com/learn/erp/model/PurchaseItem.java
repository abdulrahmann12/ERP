package com.learn.erp.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "purchase_items", indexes = { @Index(name = "idx_purchase_item_purchase", columnList = "purchase_id"),
		@Index(name = "idx_purchase_item_product", columnList = "product_id") })
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PurchaseItem {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long purchaseItemId;

	@ManyToOne
	@JoinColumn(name = "purchase_id", nullable = false)
	private Purchase purchase;

	@ManyToOne
	@JoinColumn(name = "product_id", nullable = false)
	private Product product;

	private Integer quantity;

	private BigDecimal price;

	private BigDecimal total;
}
