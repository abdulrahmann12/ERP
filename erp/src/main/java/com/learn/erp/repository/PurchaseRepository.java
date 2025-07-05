package com.learn.erp.repository;

import com.learn.erp.model.Purchase;
import com.learn.erp.model.Supplier;
import com.learn.erp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PurchaseRepository extends JpaRepository<Purchase, Long> {

    List<Purchase> findByUser(User user);

    List<Purchase> findBySupplier(Supplier supplier);

}
