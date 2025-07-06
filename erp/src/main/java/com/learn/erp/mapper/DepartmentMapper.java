package com.learn.erp.mapper;

import org.mapstruct.Mapper;

import com.learn.erp.dto.DepartmentCreateRequestDTO;
import com.learn.erp.dto.DepartmentResponseDTO;
import com.learn.erp.dto.DepartmentUpdateRequestDTO;
import com.learn.erp.model.Department;

@Mapper(componentModel = "spring")
public interface DepartmentMapper {

	DepartmentResponseDTO toDTO(Department department);
	
	Department toEntity(DepartmentCreateRequestDTO dto);
	
	Department toEntity(DepartmentUpdateRequestDTO dto);
}
