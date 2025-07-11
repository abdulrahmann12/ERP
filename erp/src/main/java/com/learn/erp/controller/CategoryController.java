package com.learn.erp.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.learn.erp.config.Messages;
import com.learn.erp.dto.BasicResponse;
import com.learn.erp.dto.CategoryCreateDTO;
import com.learn.erp.dto.CategoryResponseDTO;
import com.learn.erp.dto.CategoryUpdateDTO;
import com.learn.erp.service.CategoryService;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "Category Controller", description = "API for managing product categories and subcategories.")
@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {
	
	private final CategoryService categoryService;
	
	@PostMapping
	@PreAuthorize("hasRole('STORE_MANAGER')")
	public ResponseEntity<BasicResponse> createCategory(@RequestBody CategoryCreateDTO categoryDTO) {
		CategoryResponseDTO createdCategory = categoryService.createCategory(categoryDTO);
        return ResponseEntity.ok(new BasicResponse(Messages.ADD_CATEGORY, createdCategory));		
	}
	
	@PutMapping("/{categoryId}")
	@PreAuthorize("hasRole('STORE_MANAGER')")
	public ResponseEntity<BasicResponse> updateCategory(
			@PathVariable Long categoryId,
			@RequestBody CategoryUpdateDTO categoryDTO) {
		CategoryResponseDTO updateCategory = categoryService.updateCategory(categoryId, categoryDTO);
        return ResponseEntity.ok(new BasicResponse(Messages.UPDATE_CATEGORY, updateCategory));		
	}
	
	@GetMapping
	@PreAuthorize("isAuthenticated()")
	public ResponseEntity<List<CategoryResponseDTO>> getAllCategories(){
		return ResponseEntity.ok(categoryService.getAllCategories());
	}
	
	@GetMapping("/{categoryId}")
	@PreAuthorize("isAuthenticated()")
	public ResponseEntity<CategoryResponseDTO> getCategoryById(@PathVariable Long categoryId){
		return ResponseEntity.ok(categoryService.getCategoryById(categoryId));
	}
	
	@DeleteMapping("/{categoryId}")
	@PreAuthorize("hasRole('STORE_MANAGER')")
	public ResponseEntity<BasicResponse> deleteCategory(@PathVariable Long categoryId){
		categoryService.deleteCategory(categoryId);
		return ResponseEntity.ok(new BasicResponse(Messages.DELETE_CATEGORY));
	}
}
