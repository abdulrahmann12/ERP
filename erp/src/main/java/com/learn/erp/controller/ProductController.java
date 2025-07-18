package com.learn.erp.controller;

import com.learn.erp.config.Messages;
import com.learn.erp.dto.BasicResponse;
import com.learn.erp.dto.ProductCreateDTO;
import com.learn.erp.dto.ProductResponseDTO;
import com.learn.erp.dto.ProductUpdateDTO;
import com.learn.erp.model.User;
import com.learn.erp.service.ProductService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Tag(
	    name = "Product Controller",
	    description = "API for managing products, including creation, update, search, and category filtering."
	)
@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

	private final ProductService productService;
	
	@Operation(
		    summary = "Create Product",
		    description = "Creates a new product with optional image upload."
		)
    @PostMapping
    @PreAuthorize("hasRole('STORE_MANAGER')")
    public ResponseEntity<BasicResponse> createProduct(
            @AuthenticationPrincipal User user,
            @RequestPart("product") ProductCreateDTO dto,
            @RequestPart(value = "image", required = false) MultipartFile image
    ) throws Exception {
        ProductResponseDTO response = productService.createProduct(user.getId(), dto, image);
        return ResponseEntity.ok(new BasicResponse(Messages.ADD_PRODUCT, response));
    }
    
	@Operation(
		    summary = "Update Product",
		    description = "Updates the details and image of an existing product."
		)
    @PutMapping("/{productId}")
    @PreAuthorize("hasRole('STORE_MANAGER')")
    public ResponseEntity<BasicResponse> updateProduct(
            @AuthenticationPrincipal User user,
            @PathVariable Long productId,
            @RequestPart("product") @Valid ProductUpdateDTO dto,
            @RequestPart(value = "image", required = false) MultipartFile image
    ) throws Exception {
        ProductResponseDTO response = productService.updateProduct(user.getId(), productId, dto, image);
        return ResponseEntity.ok(new BasicResponse(Messages.UPDATE_PRODUCT, response));
    }
    
	@Operation(
		    summary = "Get Product by ID",
		    description = "Fetches a single product by its ID."
		)
    @GetMapping("/{productId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ProductResponseDTO> getProduct(@PathVariable Long productId) {
        ProductResponseDTO response = productService.getProduct(productId);
        return ResponseEntity.ok(response);
    }
    
	@Operation(
		    summary = "Get All Products",
		    description = "Returns a paginated list of all products."
		)
    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Page<ProductResponseDTO>> getAllProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Page<ProductResponseDTO> products = productService.getAllProducts(page, size);
        return ResponseEntity.ok(products);
    }
    
	@Operation(
		    summary = "Get Products by Category",
		    description = "Returns a paginated list of products that belong to the specified category."
		)
    @GetMapping("/category/{categoryId}")
	@PreAuthorize("isAuthenticated()")
	public ResponseEntity<Page<ProductResponseDTO>> getProductsByCategoryId(
			@PathVariable Long categoryId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
			){
    	Page<ProductResponseDTO> products = productService.getProductsByCategory(categoryId, page, size);
        return ResponseEntity.ok(products);	   
    }

	@Operation(
		    summary = "Search Products by Name",
		    description = "Searches products based on a keyword (partial match)."
		)
	@GetMapping("/search")
    @PreAuthorize("isAuthenticated()") 
    public List<ProductResponseDTO> fastSearchByName(@RequestParam String keyword) {
        return productService.fastSearch(keyword);
    }
}