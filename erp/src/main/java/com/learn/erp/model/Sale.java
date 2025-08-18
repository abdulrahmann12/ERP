package com.learn.erp.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "sales", indexes = { @Index(name = "idx_sales_user", columnList = "user_id"),
		@Index(name = "idx_sales_customer", columnList = "customer_id"),
		@Index(name = "idx_sales_date", columnList = "date") })
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Sale {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long saleId;

	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	@ManyToOne
	@JoinColumn(name = "customer_id", nullable = false)
	private Customer customer;

	private BigDecimal totalAmount;

	private LocalDateTime date;

	@Column(columnDefinition = "TEXT")
	private String notes;

	@OneToMany(mappedBy = "sale", cascade = CascadeType.ALL, orphanRemoval = true)
	@Builder.Default
	private List<SaleItems> items = new ArrayList<>();

	@PrePersist
	protected void onCreate() {
		this.date = LocalDateTime.now();
	}
}
