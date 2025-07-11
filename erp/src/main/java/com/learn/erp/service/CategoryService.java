package com.learn.erp.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.learn.erp.config.Messages;
import com.learn.erp.dto.CategoryCreateDTO;
import com.learn.erp.dto.CategoryResponseDTO;
import com.learn.erp.dto.CategoryUpdateDTO;
import com.learn.erp.exception.CategoryNotFoundException;
import com.learn.erp.mapper.CategoryMapper;
import com.learn.erp.model.Category;
import com.learn.erp.repository.CategoryRepository;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Service
@Validated
@RequiredArgsConstructor
public class CategoryService {
	
	private final CategoryRepository categoryRepository;
	private final CategoryMapper categoryMapper;
	
	public CategoryResponseDTO createCategory(@Valid CategoryCreateDTO dto) {
	    if (categoryRepository.findByName(dto.getName()).isPresent()) {
	        throw new IllegalArgumentException(Messages.CATEGORY_ALREADY_EXISTS);
	    }
	    Category category = categoryMapper.toEntity(dto);	
	    Category savedCategory = categoryRepository.save(category);
		return categoryMapper.toDTO(savedCategory);
	}

	public CategoryResponseDTO updateCategory(Long categoryId, @Valid CategoryUpdateDTO dto){
		
		Category existingCategory = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CategoryNotFoundException());
		
		Optional<Category> optionalCategory = categoryRepository.findByName(dto.getName());

		optionalCategory.ifPresent(otherCategory -> {
		    if (!otherCategory.getCategoryId().equals(categoryId)) {
		        throw new IllegalArgumentException(Messages.CATEGORY_ALREADY_EXISTS);
		    }
		});
		existingCategory.setName(dto.getName());
		existingCategory.setDescription(dto.getDescription());
		
		Category savedCategory = categoryRepository.save(existingCategory);
		return categoryMapper.toDTO(savedCategory);
	}
	
	public List<CategoryResponseDTO> getAllCategories(){
        return categoryRepository.findAll()
                .stream()
                .map(categoryMapper::toDTO)
                .collect(Collectors.toList());	
        }
	
	public void deleteCategory(Long categoryId) {
		Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CategoryNotFoundException());
		categoryRepository.delete(category);
	}
	
	public CategoryResponseDTO getCategoryById(Long categoryId) {
		Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CategoryNotFoundException());
		return categoryMapper.toDTO(category);
	}
}
