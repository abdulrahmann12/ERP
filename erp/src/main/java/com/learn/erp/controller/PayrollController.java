package com.learn.erp.controller;

import com.learn.erp.config.Messages;
import com.learn.erp.dto.BasicResponse;
import com.learn.erp.dto.PayrollResponseDTO;
import com.learn.erp.service.PayrollService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(
	    name = "Payroll Controller",
	    description = "API for generating and retrieving payroll information for employees."
	)
@RestController
@RequestMapping("/api/payroll")
@RequiredArgsConstructor
public class PayrollController {

    private final PayrollService payrollService;

    @Operation(
    	    summary = "Generate Payroll for All Users",
    	    description = "Generates monthly payroll for all employees for a given month and year.",
    	    tags = {"Payroll"}
    	)
    @PostMapping("/generate")
    @PreAuthorize("hasRole('ACCOUNTANT')")
    public ResponseEntity<BasicResponse> generatePayrollForAllUsers(
            @RequestParam int month,
            @RequestParam int year) {

        List<PayrollResponseDTO> response = payrollService.generatePayrollForAllUsers(month, year);
        return ResponseEntity.ok(new BasicResponse(Messages.GENERATE_PAYROLL, response));
    }

    @Operation(
    	    summary = "Get Payroll for User",
    	    description = "Retrieves the payroll information for a specific user based on the provided month and year.",
    	    tags = {"Payroll"}
    	)
    @GetMapping("/user/{userId}")
    @PreAuthorize("hasRole('ACCOUNTANT')")
    public ResponseEntity<PayrollResponseDTO> getPayrollForUser(
            @PathVariable Long userId,
            @RequestParam int month,
            @RequestParam int year) {

        PayrollResponseDTO response = payrollService.getPayrollForUser(userId, month, year);
        return ResponseEntity.ok(response);
    }
}
