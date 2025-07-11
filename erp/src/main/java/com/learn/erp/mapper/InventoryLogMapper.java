package com.learn.erp.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.learn.erp.dto.InventoryLogResponseDTO;
import com.learn.erp.model.InventoryLog;

@Mapper(componentModel = "spring",uses = ProductMapper.class)
public interface InventoryLogMapper {

	@Mapping(target = "userFullName", source = "createdBy.fullName") 
	InventoryLogResponseDTO toDTO(InventoryLog inventoryLog);
}
