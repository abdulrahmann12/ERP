package com.learn.erp.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "customers", indexes = { @Index(name = "idx_customer_email", columnList = "email", unique = true) })
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Customer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long customerId;

	private String name;

	private String phone;

	private String email;

	private String address;
}
