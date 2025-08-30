package com.learn.erp.model;


import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "payroll",uniqueConstraints = {
	    @UniqueConstraint(columnNames = {"user_id", "payroll_month", "payroll_year"})
	})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Payroll {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long payrollId;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "payroll_month")
    private int month;

    @Column(name = "payroll_year")
    private int year;

    private BigDecimal basicSalary;

    private BigDecimal bonuses;

    private BigDecimal deductions;

    private BigDecimal netSalary;

    private LocalDateTime createdAt;
    
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
}
