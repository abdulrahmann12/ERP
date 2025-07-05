package com.learn.erp.repository;

import com.learn.erp.model.Payroll;
import com.learn.erp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PayrollRepository extends JpaRepository<Payroll, Long> {

    List<Payroll> findByUser(User user);

    Optional<Payroll> findByUserAndMonthAndYear(User user, int month, int year);

}
