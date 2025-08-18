package com.learn.erp.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "employee_details", indexes = {
		@Index(name = "idx_employee_hire_date", columnList = "hireDate") }, uniqueConstraints = {
				@UniqueConstraint(name = "uk_employee_user", columnNames = "user_id"),
				@UniqueConstraint(name = "uk_employee_national_id", columnNames = "nationalId") })
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long employeeId;

	@OneToOne
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	private String nationalId;

	private LocalDate hireDate;

	private String jobTitle;

	private String emergencyContact;

	private String address;

	@Enumerated(EnumType.STRING)
	private ContractType contractType;

	public enum ContractType {
		FULL_TIME, PART_TIME, CONTRACTOR, INTERN
	}
}
