package com.learn.erp.repository;

import com.learn.erp.model.EmployeeDetails;
import com.learn.erp.model.User;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeDetailsRepository extends JpaRepository<EmployeeDetails, Long> {

    Optional<EmployeeDetails> findByUser(User user);

    boolean existsByNationalId(String nationalId);
    
	Page<EmployeeDetails> findAll(Pageable pageable);
    
    boolean existsByUser_Id(Long userId);
}
