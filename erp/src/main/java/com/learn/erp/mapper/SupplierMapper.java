package com.learn.erp.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.learn.erp.dto.SupplierCreateDTO;
import com.learn.erp.dto.SupplierDTO;
import com.learn.erp.model.Supplier;

@Mapper(componentModel = "spring")
public interface SupplierMapper {

	SupplierDTO toDTO(Supplier supplier);

	@Mapping(target = "supplierId" , ignore = true)
	Supplier toEntity(SupplierCreateDTO dto);
}
