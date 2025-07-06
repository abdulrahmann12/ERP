package com.learn.erp.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.learn.erp.dto.SaleCreateDTO;
import com.learn.erp.dto.SaleItemCreateDTO;
import com.learn.erp.dto.SaleItemResponseDTO;
import com.learn.erp.dto.SaleResponseDTO;
import com.learn.erp.model.Sale;
import com.learn.erp.model.SaleItems;

@Mapper(componentModel = "spring", uses = {UserMapper.class, CustomerMapper.class})

public interface SaleMapper {

	SaleResponseDTO toDTO(Sale sale);
	
	@Mapping(target = "customer.customerId", source = "customerId")
	@Mapping(target = "items", ignore = true)
	Sale toEntity(SaleCreateDTO dto);
	
	
	SaleItemResponseDTO toDTO(SaleItems item);
	
	@Mapping(target = "product.productId", source = "productId")
	SaleItems toEntity(SaleItemCreateDTO dto);
	
	List<SaleItems> toEntities(List<SaleItemCreateDTO> dtoList);
	List<SaleItemResponseDTO> toDTOS(List<SaleItems> items);
}
