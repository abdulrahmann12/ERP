package com.learn.erp.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.learn.erp.dto.ProductCreateDTO;
import com.learn.erp.dto.ProductResponseDTO;
import com.learn.erp.model.Product;

@Mapper(componentModel = "spring")
public interface ProductMapper {

	@Mapping(target = "categoryName", source = "category.name")
	@Mapping(target = "userFullName", source = "createdBy.fullName")
	ProductResponseDTO toDTO(Product product);
	
	@Mapping(target = "category.categoryId", source = "categoryId")
	@Mapping(target = "createdBy", ignore = true)
	@Mapping(target = "productId", ignore = true)
	Product toEntity(ProductCreateDTO dto);
}
