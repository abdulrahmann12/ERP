package com.learn.erp.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "bonuses", indexes = { @Index(name = "idx_bonus_user_month_year", columnList = "user_id, month, year") })
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Bonus {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long bonusId;

	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	private BigDecimal amount;

	private String reason;

	private int month;

	private int year;

	private LocalDateTime createdAt;

	@PrePersist
	protected void onCreate() {
		this.createdAt = LocalDateTime.now();
	}
}
