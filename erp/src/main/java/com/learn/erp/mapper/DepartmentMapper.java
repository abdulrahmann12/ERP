package com.learn.erp.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.learn.erp.dto.DepartmentCreateRequestDTO;
import com.learn.erp.dto.DepartmentResponseDTO;
import com.learn.erp.dto.DepartmentUpdateRequestDTO;
import com.learn.erp.model.Department;

@Mapper(componentModel = "spring")
public interface DepartmentMapper {

	DepartmentResponseDTO toDTO(Department department);
	
	@Mapping(target = "departmentId", ignore = true)
	@Mapping(target = "users", ignore = true)
	@Mapping(target = "createdAt", ignore = true)
	Department toEntity(DepartmentCreateRequestDTO dto);
	
	@Mapping(target = "users", ignore = true)
	@Mapping(target = "createdAt", ignore = true)
	Department toEntity(DepartmentUpdateRequestDTO dto);
}
