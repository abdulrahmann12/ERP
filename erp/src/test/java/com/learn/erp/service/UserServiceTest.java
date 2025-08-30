package com.learn.erp.service;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.learn.erp.mapper.UserMapper;
import com.learn.erp.repository.DepartmentRepository;
import com.learn.erp.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

	@Mock
    private UserRepository userRepository;

    @Mock
    private DepartmentRepository departmentRepository;

    @Mock
    private UserMapper userMapper;

    @Mock
    private ImageService imageService;

    @Mock
    private AuthService authService;

    @InjectMocks
    private UserService userService;
	
}
