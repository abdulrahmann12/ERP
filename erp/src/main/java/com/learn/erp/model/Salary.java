package com.learn.erp.model;


import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "salaries")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Salary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long salaryId;

    @OneToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private BigDecimal basicSalary;
}
