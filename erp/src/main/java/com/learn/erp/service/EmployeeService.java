package com.learn.erp.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.learn.erp.config.Messages;
import com.learn.erp.dto.EmployeeDetailsCreateRequestDTO;
import com.learn.erp.dto.EmployeeDetailsResponseDTO;
import com.learn.erp.dto.EmployeeDetailsUpdateRequestDTO;
import com.learn.erp.exception.DuplicateResourceException;
import com.learn.erp.exception.EmployeeNotFoundException;
import com.learn.erp.exception.UserNotFoundException;
import com.learn.erp.mapper.EmployeeMapper;
import com.learn.erp.model.EmployeeDetails;
import com.learn.erp.model.User;
import com.learn.erp.repository.EmployeeDetailsRepository;
import com.learn.erp.repository.UserRepository;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Validated
public class EmployeeService {

	private final EmployeeDetailsRepository employeeDetailsRepository;
	private final EmployeeMapper employeeMapper;
	private final UserRepository userRepository;
	
	public EmployeeDetailsResponseDTO addNewEmployee(@Valid EmployeeDetailsCreateRequestDTO dto) {
		User user = userRepository.findById(dto.getUserId())
			    .orElseThrow(() -> new UserNotFoundException());
		
		if (employeeDetailsRepository.existsByUser_Id(dto.getUserId())) {
		    throw new DuplicateResourceException(Messages.EMPLOYEE_ALREADY_EXISTS);
		}
		EmployeeDetails newEmployee = employeeMapper.toEntity(dto);
		newEmployee.setUser(user);
		employeeDetailsRepository.save(newEmployee);
		return employeeMapper.toDTO(newEmployee);
	}
	
	@Transactional
	public EmployeeDetailsResponseDTO updateEmployee(Long employeeId, @Valid EmployeeDetailsUpdateRequestDTO dto) {
		EmployeeDetails existingEmployee = employeeDetailsRepository.findById(employeeId)
			    .orElseThrow(() -> new EmployeeNotFoundException());
		
		existingEmployee.setAddress(dto.getAddress());
		existingEmployee.setContractType(dto.getContractType());
		existingEmployee.setEmergencyContact(dto.getEmergencyContact());
		existingEmployee.setNationalId(dto.getNationalId());
		existingEmployee.setHireDate(dto.getHireDate());
		existingEmployee.setJobTitle(dto.getJobTitle());
		
		EmployeeDetails updatedEmployee = employeeDetailsRepository.save(existingEmployee);
		return employeeMapper.toDTO(updatedEmployee);
	}
	
	public EmployeeDetailsResponseDTO getEmployee(Long employeeId) {
		EmployeeDetails employee = employeeDetailsRepository.findById(employeeId)
			    .orElseThrow(() -> new EmployeeNotFoundException());
		return employeeMapper.toDTO(employee);
	}
	
	public Page<EmployeeDetailsResponseDTO> getAllEmployees(int page, int size) {
		Pageable pageable = PageRequest.of(page, size);
		Page<EmployeeDetails> employeesPage = employeeDetailsRepository.findAll(pageable);
		return employeesPage.map(employeeMapper::toDTO);
	}
}
