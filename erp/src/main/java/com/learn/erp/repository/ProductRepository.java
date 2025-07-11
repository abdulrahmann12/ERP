package com.learn.erp.repository;

import com.learn.erp.dto.ProductResponseDTO;
import com.learn.erp.model.Product;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    Optional<Product> findByCode(String code);
    List<Product> findByNameContainingIgnoreCase(String name);

    Page<Product> findByCategory_CategoryId(Long categoryId, Pageable pageable);

    @Query("""
    	    SELECT new com.learn.erp.dto.ProductResponseDTO(
    	        p.productId,
    	        p.name,
    	        p.code,
    	        p.description,
    	        p.price,
    	        p.stock,
    	        p.unit,
    	        p.category.name,
    	        p.createdBy.fullName
    	    )
    	    FROM Product p
    	    WHERE LOWER(p.name) LIKE LOWER(concat('%', :keyword, '%'))
    	""")
    List<ProductResponseDTO> fastSearch(@Param("keyword") String keyword);
}
