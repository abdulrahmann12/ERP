package com.learn.erp.repository;

import com.learn.erp.model.Sale;
import com.learn.erp.model.SaleItems;
import com.learn.erp.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SaleItemsRepository extends JpaRepository<SaleItems, Long> {

    List<SaleItems> findBySale(Sale sale);

    List<SaleItems> findByProduct(Product product);
}
