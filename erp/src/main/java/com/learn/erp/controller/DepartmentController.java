package com.learn.erp.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.learn.erp.config.Messages;
import com.learn.erp.dto.BasicResponse;
import com.learn.erp.dto.DepartmentCreateRequestDTO;
import com.learn.erp.dto.DepartmentResponseDTO;
import com.learn.erp.dto.DepartmentUpdateRequestDTO;
import com.learn.erp.service.DepartmentService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(
	    name = "Department Controller",
	    description = "API for managing departments (create, update, delete, and view)."
	)
@RestController
@RequestMapping("/api/departments")
@RequiredArgsConstructor
public class DepartmentController {

	private final DepartmentService departmentService;
	
	@Operation(
	        summary = "Create a new department",
	        description = "Admin users can create a new department with the necessary info."
	    )
	@PostMapping
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<BasicResponse> createDepartment(@RequestBody DepartmentCreateRequestDTO dto){
		DepartmentResponseDTO response = departmentService.createDepartment(dto);
		return ResponseEntity.ok(new BasicResponse(Messages.ADD_DEPARTMENT,response));
	}
	
	@Operation(
	        summary = "Update department",
	        description = "Update a department's data using its ID. Only ADMIN can perform this."
	    )
	@PutMapping("/{departmentId}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<BasicResponse> updateDepartment(@PathVariable Long departmentId, @RequestBody DepartmentUpdateRequestDTO dto){
		DepartmentResponseDTO response = departmentService.updateDepartment(departmentId, dto);
		return ResponseEntity.ok(new BasicResponse(Messages.DEPARTMENT_UPDATE,response));
	}
	
	@Operation(
	        summary = "Delete department",
	        description = "Remove a department from the system by ID. Only ADMIN allowed."
	    )
	@DeleteMapping("/{departmentId}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<BasicResponse> deleteDepartment(@PathVariable Long departmentId){
		departmentService.deleteDepartment(departmentId);
		return ResponseEntity.ok(new BasicResponse(Messages.DELETE_DEPARTMENT));
	}
	
	@Operation(
	        summary = "Get department by ID",
	        description = "Retrieve department information by ID. Requires authentication."
	    )
	@GetMapping("/{departmentId}")
	@PreAuthorize("isAuthenticated()")
	public ResponseEntity<DepartmentResponseDTO> getDepartment(@PathVariable Long departmentId){
		DepartmentResponseDTO response =  departmentService.getDepartment(departmentId);
		return ResponseEntity.ok(response);
	}
	
	@Operation(
	        summary = "Get all departments",
	        description = "Retrieve all departments in the system. Requires authentication."
	    )
	@GetMapping
	@PreAuthorize("isAuthenticated()")
	public ResponseEntity<List<DepartmentResponseDTO>> getAllDepartments(){
		return ResponseEntity.ok(departmentService.getAllDepartments());
	}
}
