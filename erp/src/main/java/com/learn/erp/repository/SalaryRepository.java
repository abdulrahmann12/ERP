package com.learn.erp.repository;

import com.learn.erp.model.Salary;
import com.learn.erp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SalaryRepository extends JpaRepository<Salary, Long> {

    Optional<Salary> findByUser(User user);
}
