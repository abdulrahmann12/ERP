package com.learn.erp.service;

import java.io.IOException;
import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;

import com.learn.erp.config.Messages;
import com.learn.erp.dto.ProductCreateDTO;
import com.learn.erp.dto.ProductResponseDTO;
import com.learn.erp.dto.ProductUpdateDTO;
import com.learn.erp.exception.CategoryNotFoundException;
import com.learn.erp.exception.ProductNotFoundException;
import com.learn.erp.exception.UserNotFoundException;
import com.learn.erp.mapper.ProductMapper;
import com.learn.erp.model.Category;
import com.learn.erp.model.InventoryLog;
import com.learn.erp.model.Product;
import com.learn.erp.model.User;
import com.learn.erp.repository.CategoryRepository;
import com.learn.erp.repository.InventoryLogRepository;
import com.learn.erp.repository.ProductRepository;
import com.learn.erp.repository.UserRepository;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Validated
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductMapper productMapper;
    private final InventoryLogRepository inventoryLogRepository;
    private final UserRepository userRepository;
    private final ImageService imageService;
    
    public ProductResponseDTO createProduct(Long userId, @Valid ProductCreateDTO dto, MultipartFile image) throws Exception {
        Category category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new CategoryNotFoundException());

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException());
        
        Product product = productMapper.toEntity(dto);
        product.setCategory(category);
        product.setCreatedBy(user);
    	
        if (image != null && !image.isEmpty()) {
            try {
                String imageUrl = imageService.uploadImage(image);
                product.setImage(imageUrl);
            } catch (IOException e) {
                throw new Exception(Messages.UPLOAD_IMAGE_FAILED + e.getMessage());
            }
        }
        Product savedProduct = productRepository.save(product);
        
        // Save InventoryLog
        InventoryLog log = InventoryLog.builder()
                .product(savedProduct)
                .quantityBefore(0)
                .quantityAfter(savedProduct.getStock())
                .actionType(InventoryLog.ActionType.PURCHASE)
                .note(Messages.CREATE_PRODUCT)
                .createdBy(user)
                .build();
        
        inventoryLogRepository.save(log);

        return productMapper.toDTO(savedProduct);
    }
    
    @Transactional
    public ProductResponseDTO updateProduct(Long userId, Long productId, @Valid ProductUpdateDTO dto, MultipartFile image) throws Exception {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException());

        Category category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new CategoryNotFoundException());

        int quantityBefore = product.getStock();

        product.setName(dto.getName());
        product.setCode(dto.getCode());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        product.setUnit(dto.getUnit());
        product.setCategory(category);
        
        boolean stockChanged = dto.getStock() != null && !dto.getStock().equals(product.getStock());

        if (dto.getStock() != null) {
            product.setStock(dto.getStock());
        }
        
        if (image != null && !image.isEmpty()) {
            try {
                String imageUrl = imageService.uploadImage(image);
                product.setImage(imageUrl);
            } catch (IOException e) {
                throw new Exception(Messages.UPLOAD_IMAGE_FAILED + e.getMessage());
            }
        }

        Product updated = productRepository.save(product);

        // Save InventoryLog
        if (stockChanged) {
            InventoryLog log = InventoryLog.builder()
                    .product(updated)
                    .quantityBefore(quantityBefore)
                    .quantityAfter(updated.getStock())
                    .actionType(InventoryLog.ActionType.ADJUSTMENT)
                    .note(Messages.STOCK_UPDATED)
                    .createdBy(userRepository.findById(userId).orElse(null))
                    .build();

            inventoryLogRepository.save(log);
        }
        return productMapper.toDTO(updated);
    }
    
    public Page<ProductResponseDTO> getAllProducts(int page, int size) {
    	Pageable pageable = PageRequest.of(page, size);
        return productRepository.findAll(pageable)
                .map(productMapper::toDTO);
    }
    
    public ProductResponseDTO getProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException());
        return productMapper.toDTO(product);
    }
    
    public Page<ProductResponseDTO> getProductsByCategory(Long categoryId, int page, int size) {
    	categoryRepository.findById(categoryId)
    		.orElseThrow(() -> new CategoryNotFoundException());
    	Pageable pageable = PageRequest.of(page, size);
        return productRepository.findByCategory_CategoryId(categoryId, pageable)
                .map(productMapper::toDTO);
    }
    
	@Cacheable(value = "products", key = "#keyword")
	public List<ProductResponseDTO> fastSearch(String keyword) {
        return productRepository.fastSearch(keyword);
    }
}
