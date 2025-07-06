package com.learn.erp.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.learn.erp.dto.SalaryCreateDTO;
import com.learn.erp.dto.SalaryResponseDTO;
import com.learn.erp.dto.SalaryUpdateDTO;
import com.learn.erp.model.Salary;

@Mapper(componentModel = "spring", uses = UserMapper.class)
public interface SalaryMapper {

	SalaryResponseDTO toDTO(Salary salary);
	
	@Mapping(target = "user.id", source = "userId")
	@Mapping(target = "salaryId" , ignore = true)
	Salary toEntity(SalaryCreateDTO dto);
	
	@Mapping(target = "user.id", source = "userId")
	Salary toEntity(SalaryUpdateDTO dto);
}
