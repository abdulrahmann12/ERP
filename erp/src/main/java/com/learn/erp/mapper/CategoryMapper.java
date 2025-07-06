package com.learn.erp.mapper;

import org.mapstruct.Mapper;

import com.learn.erp.dto.CategoryCreateDTO;
import com.learn.erp.dto.CategoryResponseDTO;
import com.learn.erp.dto.CategoryUpdateDTO;
import com.learn.erp.model.Category;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

	CategoryResponseDTO toDTO(Category category);
	
	Category toEntity(CategoryCreateDTO dto);
	
	Category toEntity(CategoryUpdateDTO dto);
}
