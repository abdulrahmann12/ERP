package com.learn.erp.repository;

import com.learn.erp.model.Sale;
import com.learn.erp.model.Customer;
import com.learn.erp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SaleRepository extends JpaRepository<Sale, Long> {

    List<Sale> findByUser(User user);

    List<Sale> findByCustomer(Customer customer);
}
