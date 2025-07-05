package com.learn.erp.repository;

import com.learn.erp.model.Product;
import com.learn.erp.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    Optional<Product> findByCode(String code);
    List<Product> findByNameContainingIgnoreCase(String name);

    List<Product> findByCategory(Category category);

}
