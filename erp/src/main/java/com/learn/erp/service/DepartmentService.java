package com.learn.erp.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.learn.erp.config.Messages;
import com.learn.erp.dto.DepartmentCreateRequestDTO;
import com.learn.erp.dto.DepartmentResponseDTO;
import com.learn.erp.dto.DepartmentUpdateRequestDTO;
import com.learn.erp.exception.DepartmentAlreadyExistsException;
import com.learn.erp.exception.DepartmentNotFoundException;
import com.learn.erp.mapper.DepartmentMapper;
import com.learn.erp.model.Department;
import com.learn.erp.repository.DepartmentRepository;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Service
@Validated
@RequiredArgsConstructor
public class DepartmentService {

	private final DepartmentRepository departmentRepository;
	private final DepartmentMapper departmentMapper;
	
	@Transactional
    @CacheEvict(value = {"departments", "allDepartments"}, allEntries = true)
	public DepartmentResponseDTO createDepartment(@Valid DepartmentCreateRequestDTO dto) {
		Department department = departmentMapper.toEntity(dto);
		departmentRepository.findByName(dto.getName())
        .ifPresent(u -> { throw new DepartmentAlreadyExistsException();});
	
		Department savedDepartment = departmentRepository.save(department);	
		return departmentMapper.toDTO(savedDepartment);
	}
	
	@Transactional
    @CacheEvict(value = {"departments", "allDepartments"}, allEntries = true)
	public DepartmentResponseDTO updateDepartment(Long departmentId, @Valid DepartmentUpdateRequestDTO dto) {
		Department department = departmentRepository.findById(departmentId)
				 .orElseThrow(() -> new DepartmentNotFoundException());
		
		departmentRepository.findByName(dto.getName())
	    .filter(dep -> !dep.getDepartmentId().equals(departmentId))
	    .ifPresent(dep -> { throw new DepartmentAlreadyExistsException();});
		
		 department.setName(dto.getName());
		 department.setDescription(dto.getDescription());
		
		Department savedDepartment = departmentRepository.save(department);	
		return departmentMapper.toDTO(savedDepartment);
	}
	
    @CacheEvict(value = {"departments", "allDepartments"}, allEntries = true)
	public void deleteDepartment(Long departmentId) {
		Department department = departmentRepository.findById(departmentId)
				 .orElseThrow(() -> new DepartmentNotFoundException());
	    if (!department.getUsers().isEmpty()) {
	        throw new IllegalStateException(Messages.CANNOT_DELETE_DEPARTMENT);
	    }
		departmentRepository.delete(department);
	}
	
	public DepartmentResponseDTO getDepartment(Long departmentId){
		Department department = departmentRepository.findById(departmentId)
				 .orElseThrow(() -> new DepartmentNotFoundException());
		return departmentMapper.toDTO(department);
	}
	
    @Cacheable(value = "allDepartments")
	public List<DepartmentResponseDTO> getAllDepartments(){
		return departmentRepository.findAll()
                .stream()
                .map(departmentMapper::toDTO)
                .collect(Collectors.toList());	
	}
}
