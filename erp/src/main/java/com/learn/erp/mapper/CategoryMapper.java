package com.learn.erp.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.learn.erp.dto.CategoryCreateDTO;
import com.learn.erp.dto.CategoryResponseDTO;
import com.learn.erp.dto.CategoryUpdateDTO;
import com.learn.erp.model.Category;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

	CategoryResponseDTO toDTO(Category category);
	
	@Mapping(target = "categoryId", ignore = true)
	@Mapping(target = "products", ignore = true)
	Category toEntity(CategoryCreateDTO dto);
	
	@Mapping(target = "categoryId", ignore = true)
	@Mapping(target = "products", ignore = true)
	Category toEntity(CategoryUpdateDTO dto);
}
