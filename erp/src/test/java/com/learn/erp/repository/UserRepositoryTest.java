package com.learn.erp.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.learn.erp.model.Department;
import com.learn.erp.model.User;

@DataJpaTest
public class UserRepositoryTest {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private DepartmentRepository departmentRepository;
	
	
	
	@BeforeEach
	void setUp() {
	    Department department = new Department();
	    department.setName("IT");
	    department.setDescription("IT");
	    department = departmentRepository.save(department);

	    User user = new User();
	    user.setEmail("test@example.com");
	    user.setUsername("testUser");
	    user.setDepartment(department);
	    userRepository.save(user);
	}
	    
	    
	@Test
	@DisplayName("Find By Email")
	public void testFindByEmail() {
		
        // Act
        Optional<User> result = userRepository.findByEmail("test@example.com");
        
        // Assert
        assertThat(result).isPresent();
        assertThat(result.get().getUsername()).isEqualTo("testUser");	
	}
	
	@Test
	@DisplayName("Find all users")
	public void testFindAllUser() {
		
		// Act
		List<User> users = userRepository.findAll();
		
		// Assert
		assertThat(users).isNotNull();
		assertThat(users.size()).isEqualTo(1);
	}
	
	@Test
	@DisplayName("Check Department Name")
	public void teatChackDepartmentName() {
		
		Optional<Department> deOptional = departmentRepository.findByName("IT");
		
		assertThat(deOptional.get().getDescription()).isEqualTo("IT");
	}	
}