package com.learn.erp.mapper;

import org.mapstruct.Mapper;

import com.learn.erp.dto.PayrollResponseDTO;
import com.learn.erp.model.Payroll;

@Mapper(componentModel = "spring", uses = UserMapper.class)
public interface PayrollMapper {

	PayrollResponseDTO toDTO(Payroll payroll);
}
