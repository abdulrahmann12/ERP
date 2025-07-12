package com.learn.erp.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.learn.erp.config.Messages;
import com.learn.erp.dto.SalaryCreateDTO;
import com.learn.erp.dto.SalaryResponseDTO;
import com.learn.erp.dto.SalaryUpdateDTO;
import com.learn.erp.exception.DuplicateResourceException;
import com.learn.erp.exception.SalaryNotFoundException;
import com.learn.erp.exception.UserNotFoundException;
import com.learn.erp.mapper.SalaryMapper;
import com.learn.erp.model.Salary;
import com.learn.erp.model.User;
import com.learn.erp.repository.SalaryRepository;
import com.learn.erp.repository.UserRepository;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Service
@Validated
@RequiredArgsConstructor
public class SalaryService {

	private final SalaryRepository salaryRepository;
	private final SalaryMapper salaryMapper;
	private final UserRepository userRepository;
	
	public SalaryResponseDTO createUserSalary(@Valid SalaryCreateDTO dto) {
		User user = userRepository.findById(dto.getUserId())
				.orElseThrow(() -> new UserNotFoundException());
		
		boolean exists = salaryRepository.findByUserId(dto.getUserId()).isPresent();
		if (exists) {
			throw new DuplicateResourceException(Messages.SALARY_ALREADY_EXISTS);
		}
		Salary salary = salaryMapper.toEntity(dto);
		salary.setUser(user);
		
		Salary savedSalary = salaryRepository.save(salary);
		return salaryMapper.toDTO(savedSalary);
	}
	
	public SalaryResponseDTO updateUserSalary(Long userId, @Valid SalaryUpdateDTO dto) {
		userRepository.findById(userId)
				.orElseThrow(() -> new UserNotFoundException());
		Salary salary = salaryRepository.findByUserId(userId)
				.orElseThrow(() -> new SalaryNotFoundException());
		salary.setBasicSalary(dto.getBasicSalary());
		
		Salary updatedSalary = salaryRepository.save(salary);
		return salaryMapper.toDTO(updatedSalary);
	}
	
	public SalaryResponseDTO getUserSalary(Long userId) {
		userRepository.findById(userId)
			.orElseThrow(() -> new UserNotFoundException());
		Salary salary = salaryRepository.findByUserId(userId)
				.orElseThrow(() -> new SalaryNotFoundException());
		return salaryMapper.toDTO(salary);
	}
	
	public List<SalaryResponseDTO> getAllUsersSalaries() {
		List<Salary> salaries = salaryRepository.findAll();
		return salaries.stream().map(salaryMapper::toDTO).collect(Collectors.toList());
	}
}
