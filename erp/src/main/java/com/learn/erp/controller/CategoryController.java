package com.learn.erp.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
import com.learn.erp.model.User;
import com.learn.erp.service.CategoryService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "Category Controller", description = "API for managing product categories and subcategories.")
@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {
	
	private final CategoryService categoryService;
	
	@Operation(
	        summary = "Create a new category",
	        description = "Allows the store manager to create a new product category."
	)
	@PostMapping
	@PreAuthorize("hasRole('STORE_MANAGER')")
	public ResponseEntity<BasicResponse> createCategory(@AuthenticationPrincipal User user, @RequestBody CategoryCreateDTO categoryDTO) {
		CategoryResponseDTO createdCategory = categoryService.createCategory(user.getId(), categoryDTO);
        return ResponseEntity.ok(new BasicResponse(Messages.ADD_CATEGORY, createdCategory));		
	}
	
	@Operation(
	        summary = "Update existing category",
	        description = "Allows the store manager to update an existing product category by ID."
	)
	@PutMapping("/{categoryId}")
	@PreAuthorize("hasRole('STORE_MANAGER')")
	public ResponseEntity<BasicResponse> updateCategory(
			@PathVariable Long categoryId,
			@RequestBody CategoryUpdateDTO categoryDTO) {
		CategoryResponseDTO updateCategory = categoryService.updateCategory(categoryId, categoryDTO);
        return ResponseEntity.ok(new BasicResponse(Messages.UPDATE_CATEGORY, updateCategory));		
	}
	
	@Operation(
	        summary = "Get all categories",
	        description = "Retrieve a list of all available product categories."
	)
	@GetMapping
	@PreAuthorize("isAuthenticated()")
	public ResponseEntity<List<CategoryResponseDTO>> getAllCategories(){
		return ResponseEntity.ok(categoryService.getAllCategories());
	}
	
	@Operation(
	        summary = "Get category by ID",
	        description = "Retrieve detailed information of a specific category using its ID."
	)
	@GetMapping("/{categoryId}")
	@PreAuthorize("isAuthenticated()")
	public ResponseEntity<CategoryResponseDTO> getCategoryById(@PathVariable Long categoryId){
		return ResponseEntity.ok(categoryService.getCategoryById(categoryId));
	}
	
	@Operation(
	        summary = "Delete existing category",
	        description = "Remove a category by its ID (only STORE_MANAGER allowed)."
	)
	@DeleteMapping("/{categoryId}")
	@PreAuthorize("hasRole('STORE_MANAGER')")
	public ResponseEntity<BasicResponse> deleteCategory(@PathVariable Long categoryId){
		categoryService.deleteCategory(categoryId);
		return ResponseEntity.ok(new BasicResponse(Messages.DELETE_CATEGORY));
	}
}