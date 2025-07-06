package com.learn.erp.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.learn.erp.dto.ProductCreateDTO;
import com.learn.erp.dto.ProductResponseDTO;
import com.learn.erp.dto.ProductUpdateDTO;
import com.learn.erp.model.Product;

@Mapper(componentModel = "spring")
public interface ProductMapper {

	@Mapping(target = "categoryName", source = "category.name")
	ProductResponseDTO toDTO(Product product);
	
	@Mapping(target = "category.id", source = "categoryId")
	Product toEntity(ProductCreateDTO dto);
	
	@Mapping(target = "category.id", source = "categoryId")
	Product toEntity(ProductUpdateDTO dto);
	
}
