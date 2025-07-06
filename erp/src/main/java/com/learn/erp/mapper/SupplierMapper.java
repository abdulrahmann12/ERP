package com.learn.erp.mapper;

import org.mapstruct.Mapper;

import com.learn.erp.dto.CustomerCreateDTO;
import com.learn.erp.dto.CustomerDTO;
import com.learn.erp.dto.SupplierCreateDTO;
import com.learn.erp.dto.SupplierDTO;
import com.learn.erp.model.Customer;
import com.learn.erp.model.Supplier;

@Mapper(componentModel = "spring")
public interface SupplierMapper {

	SupplierDTO toDTO(Supplier supplier);

	Supplier toEntity(SupplierCreateDTO dto);
}
