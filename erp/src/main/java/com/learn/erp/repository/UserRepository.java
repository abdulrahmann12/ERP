package com.learn.erp.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.learn.erp.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
	Optional<User> findByEmail(String email);
	Optional<User> findByUsername(String username);
	Page<User> findAll(Pageable pageable);

	Page<User> findAllByDepartment_DepartmentId(Long departmentId, Pageable pageable);
}
