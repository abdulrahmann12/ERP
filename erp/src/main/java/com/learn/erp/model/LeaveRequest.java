package com.learn.erp.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "leave_requests", 
	   indexes = {
			   @Index(name = "idx_leave_user", columnList = "user_id"),
			   @Index(name = "idx_leave_status", columnList = "status"),
			   @Index(name = "idx_leave_startDate", columnList = "startDate"), })
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LeaveRequest {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long requestId;

	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	private LocalDate startDate;

	private LocalDate endDate;

	@Column(columnDefinition = "TEXT")
	private String reason;

	private LocalDateTime createdAt;

	@Enumerated(EnumType.STRING)
	private Status status;

	public enum Status {
		PENDING, APPROVED, REJECTED
	}

	@PrePersist
	protected void onCreate() {
		this.createdAt = LocalDateTime.now();
	}
}
