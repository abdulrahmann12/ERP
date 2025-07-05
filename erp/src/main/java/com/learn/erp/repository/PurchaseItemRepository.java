package com.learn.erp.repository;

import com.learn.erp.model.Purchase;
import com.learn.erp.model.PurchaseItem;
import com.learn.erp.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PurchaseItemRepository extends JpaRepository<PurchaseItem, Long> {

    List<PurchaseItem> findByPurchase(Purchase purchase);

    List<PurchaseItem> findByProduct(Product product);
}
