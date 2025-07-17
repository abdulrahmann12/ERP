package com.learn.erp.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.learn.erp.config.Messages;
import com.learn.erp.dto.BasicResponse;
import com.learn.erp.dto.SalaryCreateDTO;
import com.learn.erp.dto.SalaryResponseDTO;
import com.learn.erp.dto.SalaryUpdateDTO;
import com.learn.erp.service.SalaryService;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(
	    name = "Salary Controller",
	    description = "API for creating, updating, and retrieving employee salary information."
	)
@RestController
@RequestMapping("/api/salaries")
@RequiredArgsConstructor
public class SalaryController {

	private final SalaryService salaryService;
	
	@PostMapping
	@PreAuthorize("hasRole('HR')")
	public ResponseEntity<BasicResponse> createSalary(@RequestBody SalaryCreateDTO dto){
		SalaryResponseDTO response = salaryService.createUserSalary(dto);
		return ResponseEntity.ok(new BasicResponse(Messages.ADD_SALARY, response));
	}
	
	@PutMapping("/{userId}")
	@PreAuthorize("hasRole('HR')")
	public ResponseEntity<BasicResponse> updateSalary(@PathVariable Long userId, @RequestBody SalaryUpdateDTO dto){
		SalaryResponseDTO response = salaryService.updateUserSalary(userId, dto);
		return ResponseEntity.ok(new BasicResponse(Messages.UPDATE_SALARY, response));
	}
	
	@GetMapping("/{userId}")
	@PreAuthorize("hasRole('HR')")
	public ResponseEntity<SalaryResponseDTO> getUserSalary(@PathVariable Long userId){
		SalaryResponseDTO response = salaryService.getUserSalary(userId);
		return ResponseEntity.ok(response);
	}
	
	@GetMapping
	@PreAuthorize("hasRole('HR')")
	public ResponseEntity<List<SalaryResponseDTO>> getAllUsersSalaries(){
		List<SalaryResponseDTO> response = salaryService.getAllUsersSalaries();
		return ResponseEntity.ok(response);
	}
}
