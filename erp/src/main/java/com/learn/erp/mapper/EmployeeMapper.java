package com.learn.erp.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.learn.erp.dto.EmployeeDetailsCreateRequestDTO;
import com.learn.erp.dto.EmployeeDetailsResponseDTO;
import com.learn.erp.dto.EmployeeDetailsUpdateRequestDTO;
import com.learn.erp.model.EmployeeDetails;

@Mapper(componentModel = "spring", uses = UserMapper.class)
public interface EmployeeMapper {

	EmployeeDetailsResponseDTO toDTO(EmployeeDetails employeeDetails);
	
	@Mapping(target = "user.id", source = "userId")
	EmployeeDetails toEntity(EmployeeDetailsCreateRequestDTO dto);
	
	@Mapping(target = "user.id", source = "userId")
	EmployeeDetails toEntity(EmployeeDetailsUpdateRequestDTO dto);
}
