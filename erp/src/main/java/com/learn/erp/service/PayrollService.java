package com.learn.erp.service;


import com.learn.erp.dto.PayrollResponseDTO;
import com.learn.erp.exception.PayrollAlreadyExistsException;
import com.learn.erp.exception.PayrollNotFoundException;
import com.learn.erp.exception.SalaryNotFoundException;
import com.learn.erp.exception.UserNotFoundException;
import com.learn.erp.mapper.PayrollMapper;
import com.learn.erp.model.*;
import com.learn.erp.repository.*;

import lombok.RequiredArgsConstructor;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PayrollService {

    private final UserRepository userRepository;
    private final SalaryRepository salaryRepository;
    private final BonusRepository bonusRepository;
    private final AttendanceRepository attendanceRepository;
    private final PayrollRepository payrollRepository;
    private final PayrollMapper payrollMapper;

    private static final BigDecimal DEDUCTION_PER_ABSENCE = new BigDecimal("100");

    public List<PayrollResponseDTO> generatePayrollForAllUsers(int month, int year) {
        List<User> users = userRepository.findAll();

        return users.stream().map(user -> {
            // Check for existing payroll
            if (payrollRepository.findByUserAndMonthAndYear(user, month, year).isPresent()) {
                throw new PayrollAlreadyExistsException(user.getFullName(), month, year);
            }

            // Salary
            Salary salary = salaryRepository.findByUserId(user.getId())
                    .orElseThrow(() -> new SalaryNotFoundException());

            // Bonuses
            BigDecimal bonuses = bonusRepository.findTotalBonusByUserIdAndMonthAndYear(user.getId(), month, year);

            // Absences
            long absences = attendanceRepository.countByUserAndStatusAndMonthAndYear(user, Attendance.Status.ABSENT, month, year);
            BigDecimal deductions = DEDUCTION_PER_ABSENCE.multiply(BigDecimal.valueOf(absences));

            // Net Salary
            BigDecimal netSalary = salary.getBasicSalary().add(bonuses).subtract(deductions);

            Payroll payroll = Payroll.builder()
                    .user(user)
                    .month(month)
                    .year(year)
                    .basicSalary(salary.getBasicSalary())
                    .bonuses(bonuses)
                    .deductions(deductions)
                    .netSalary(netSalary)
                    .build();

            payrollRepository.save(payroll);
            return payrollMapper.toDTO(payroll);

        }).collect(Collectors.toList());
    }
    
    public PayrollResponseDTO getPayrollForUser(Long userId, int month, int year) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException());

        Payroll payroll = payrollRepository.findByUserAndMonthAndYear(user, month, year)
                .orElseThrow(() -> new PayrollNotFoundException());

        return payrollMapper.toDTO(payroll);
    }

    @Scheduled(cron = "0 0 1 1 * *") 
    public void autoGeneratePayrollMonthly() {
        LocalDate now = LocalDate.now().minusMonths(1);
        int month = now.getMonthValue();
        int year = now.getYear();

        try {
            generatePayrollForAllUsers(month, year);
            System.out.println("Payroll generated for " + month + "/" + year);
        } catch (Exception e) {
            System.err.println("Error generating payroll: " + e.getMessage());
        }
    }
}
