package com.learn.erp.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.learn.erp.dto.DepartmentCreateRequestDTO;
import com.learn.erp.dto.DepartmentResponseDTO;
import com.learn.erp.exception.DepartmentAlreadyExistsException;
import com.learn.erp.mapper.DepartmentMapper;
import com.learn.erp.model.Department;
import com.learn.erp.repository.DepartmentRepository;

@ExtendWith(MockitoExtension.class)
public class DepartmentServiceTest {

	@Mock
	private DepartmentRepository departmentRepository;

	@Mock
	private DepartmentMapper departmentMapper;
	
    @InjectMocks
    private DepartmentService departmentService;
        
    private DepartmentCreateRequestDTO requestDTO;
    private Department department;
    private DepartmentResponseDTO responseDTO;
    
    @BeforeEach
    public void setUp() {
        requestDTO = new DepartmentCreateRequestDTO();
        requestDTO.setName("IT");
        requestDTO.setDescription("Information Technology");

        department = new Department();
        department.setDepartmentId(1L);
        department.setName("IT");
        department.setDescription("Information Technology");

        responseDTO = new DepartmentResponseDTO();
        responseDTO.setDepartmentId(1L);
        responseDTO.setName("IT");
        responseDTO.setDescription("Information Technology");
    }
    
    @Test
    @DisplayName("Create Department")
    public void createDepartment_ShouldReturnResponseDTO_WhenSuccess() {

    	// Arrange
    	when(departmentRepository.save(department)).thenReturn(department);
    	when(departmentMapper.toEntity(requestDTO)).thenReturn(department);
    	when(departmentMapper.toDTO(department)).thenReturn(responseDTO);
    	
    	// Act
    	DepartmentResponseDTO result = departmentService.createDepartment(requestDTO);
    	
    	// Assert
    	assertNotNull(result);
    	assertEquals(responseDTO.getDepartmentId(), result.getDepartmentId());
    	assertEquals(responseDTO.getName(), result.getName());
    	assertEquals(responseDTO.getDescription(), result.getDescription());
    	
    	// Verify
    	verify(departmentMapper, times(1)).toEntity(requestDTO);
    	verify(departmentMapper, times(1)).toDTO(department);
    	verify(departmentRepository, times(1)).save(department);
    }   
    
    
    @Test
    void testCreateDepartment_DepartmentAlreadyExists() {
        // Arrange
        DepartmentCreateRequestDTO dto = new DepartmentCreateRequestDTO();
        dto.setName("HR");
        dto.setDescription("Human Resources");

        Department existingDepartment = new Department();
        existingDepartment.setDepartmentId(1L);
        existingDepartment.setName("HR");

        when(departmentRepository.findByName("HR"))
                .thenReturn(Optional.of(existingDepartment));

        // Act & Assert
        assertThrows(DepartmentAlreadyExistsException.class, () -> {
            departmentService.createDepartment(dto);
        });

        // Verify
        verify(departmentRepository, times(0)).save(existingDepartment);
    }

}