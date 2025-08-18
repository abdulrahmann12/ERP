package com.learn.erp.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "attendance", indexes = { @Index(name = "idx_attendance_user", columnList = "user_id"),
		@Index(name = "idx_attendance_date", columnList = "date"),
		@Index(name = "idx_attendance_user_date", columnList = "user_id, date") })
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Attendance {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long attendanceId;

	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	private LocalDate date;

	private LocalTime checkIn;

	private LocalTime checkOut;

	private int workingHours;

	@Enumerated(EnumType.STRING)
	private Status status;

	public enum Status {
		PRESENT, ABSENT, LATE, ON_LEAVE, AUTO_CHECKED_OUT
	}

	@PrePersist
	protected void onCreate() {
		if (this.date == null) {
			this.date = LocalDate.now();
		}
	}
}
