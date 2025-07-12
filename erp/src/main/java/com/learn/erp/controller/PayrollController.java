package com.learn.erp.controller;

import com.learn.erp.config.Messages;
import com.learn.erp.dto.BasicResponse;
import com.learn.erp.dto.PayrollResponseDTO;
import com.learn.erp.service.PayrollService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payroll")
@RequiredArgsConstructor
public class PayrollController {

    private final PayrollService payrollService;

    @PostMapping("/generate")
    @PreAuthorize("hasRole('HR')")
    public ResponseEntity<BasicResponse> generatePayrollForAllUsers(
            @RequestParam int month,
            @RequestParam int year) {

        List<PayrollResponseDTO> response = payrollService.generatePayrollForAllUsers(month, year);
        return ResponseEntity.ok(new BasicResponse(Messages.GENERATE_PAYROLL, response));
    }

    @GetMapping("/user/{userId}")
    @PreAuthorize("hasRole('HR')")
    public ResponseEntity<PayrollResponseDTO> getPayrollForUser(
            @PathVariable Long userId,
            @RequestParam int month,
            @RequestParam int year) {

        PayrollResponseDTO response = payrollService.getPayrollForUser(userId, month, year);
        return ResponseEntity.ok(response);
    }
}
