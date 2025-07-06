package com.learn.erp.mapper;

import org.mapstruct.Mapper;

import com.learn.erp.dto.InventoryLogResponseDTO;
import com.learn.erp.model.InventoryLog;

@Mapper(componentModel = "spring",uses = ProductMapper.class)
public interface InventoryLogMapper {

	InventoryLogResponseDTO toDTO(InventoryLog inventoryLog);
}
