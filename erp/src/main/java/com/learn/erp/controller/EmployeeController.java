package com.learn.erp.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.learn.erp.config.Messages;
import com.learn.erp.dto.BasicResponse;
import com.learn.erp.dto.EmployeeDetailsCreateRequestDTO;
import com.learn.erp.dto.EmployeeDetailsResponseDTO;
import com.learn.erp.dto.EmployeeDetailsUpdateRequestDTO;
import com.learn.erp.service.EmployeeService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/employees")
public class EmployeeController {

	private final EmployeeService employeeService;
	
	@PostMapping
	@PreAuthorize("hasRole('HR')")
	public ResponseEntity<BasicResponse> addNewEmployee(@RequestBody EmployeeDetailsCreateRequestDTO dto){
		EmployeeDetailsResponseDTO response = employeeService.addNewEmployee(dto);
		return ResponseEntity.ok(new BasicResponse(Messages.ADD_EMPLOYEE, response));
	}
	
	@PutMapping("/{employeeId}")
	@PreAuthorize("hasRole('HR')")
	public ResponseEntity<BasicResponse> updateEmployee(@PathVariable Long employeeId, @RequestBody EmployeeDetailsUpdateRequestDTO dto){
		EmployeeDetailsResponseDTO response = employeeService.updateEmployee(employeeId, dto);
		return ResponseEntity.ok(new BasicResponse(Messages.UPDATE_EMPLOYEE, response));
	}

    @GetMapping("/{employeeId}")
    @PreAuthorize("hasRole('HR')")
    public ResponseEntity<EmployeeDetailsResponseDTO> getEmployee(@PathVariable Long employeeId) {
        EmployeeDetailsResponseDTO employee = employeeService.getEmployee(employeeId);
        return ResponseEntity.ok(employee);
    }
    
    @GetMapping
    @PreAuthorize("hasRole('HR')")
    public ResponseEntity<Page<EmployeeDetailsResponseDTO>> getAllEmployees(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(employeeService.getAllEmployees(page, size));
    }
}
