package com.learn.erp.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.learn.erp.dto.EmployeeDetailsCreateRequestDTO;
import com.learn.erp.dto.EmployeeDetailsResponseDTO;
import com.learn.erp.model.EmployeeDetails;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {

	@Mapping(target = "userId", source = "user.id")
	EmployeeDetailsResponseDTO toDTO(EmployeeDetails employeeDetails);
	
	@Mapping(target = "user.id", source = "userId")
	@Mapping(target = "employeeId" , ignore = true)
	EmployeeDetails toEntity(EmployeeDetailsCreateRequestDTO dto);
}
