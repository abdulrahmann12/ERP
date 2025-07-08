package com.learn.erp.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class DepartmentUpdateRequestDTO {
		
	@NotBlank(message = "Name is required")
    private String name;
	
	@NotBlank(message = "Description is required")
    private String description;
}