package com.learn.erp.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;

import com.learn.erp.config.Messages;
import com.learn.erp.dto.AdminUpdateUserRequestDTO;
import com.learn.erp.dto.AdminViewUserResponseDTO;
import com.learn.erp.dto.UserUpdateRequestDTO;
import com.learn.erp.dto.ViewUserResponseDTO;
import com.learn.erp.exception.DepartmentNotFoundException;
import com.learn.erp.exception.EmailAlreadyExistsException;
import com.learn.erp.exception.UserNotFoundException;
import com.learn.erp.exception.UsernameAlreadyExistsException;
import com.learn.erp.mapper.UserMapper;
import com.learn.erp.model.Department;
import com.learn.erp.model.User;
import com.learn.erp.model.User.Role;
import com.learn.erp.repository.DepartmentRepository;
import com.learn.erp.repository.UserRepository;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Validated
public class UserService {

	private final UserRepository userRepository;
	private final UserMapper userMapper;
	private final ImageService imageService;
	private final AuthService authService;
	private final DepartmentRepository departmentRepository;
	
	public AdminViewUserResponseDTO adminUpdateUser(Long userId, @Valid AdminUpdateUserRequestDTO dto) {
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new UserNotFoundException());
		
		Department department = departmentRepository.findById(dto.getDepartmentId())
				 .orElseThrow(() -> new DepartmentNotFoundException());
		
		user.setEmail(dto.getEmail());
		user.setFullName(dto.getFullName());
		user.setPhone(dto.getPhone());
		user.setRole(dto.getRole());
		user.setActive(dto.getActive());
		user.setDepartment(department);
		
		User updatedUser = userRepository.save(user);
		return userMapper.toAdminViewUserDTO(updatedUser);
	}
	
	public ViewUserResponseDTO updateUser(Long userId, @Valid UserUpdateRequestDTO dto, String token) {
		User existingUser = userRepository.findById(userId)
				.orElseThrow(() -> new UserNotFoundException());
		
	    userRepository.findByUsername(dto.getUsername())
	    .filter(user -> !user.getId().equals(userId))
	    .ifPresent(user -> { throw new UsernameAlreadyExistsException();});
	    
		existingUser.setFullName(dto.getFullName());
		existingUser.setPhone(dto.getPhone());
		existingUser.setUsername(dto.getUsername());
		existingUser.setGender(dto.getGender());
		existingUser.setBirthDate(dto.getBirthDate());

	    if(!existingUser.getEmail().equals(dto.getEmail())) {
	        userRepository.findByEmail(dto.getEmail()).ifPresent(u -> {
	            throw new EmailAlreadyExistsException();
	        });
	    	authService.logout(token);
	    	existingUser.setEmail(dto.getEmail());
	    }
		User updatedUser = userRepository.save(existingUser);
		return userMapper.toViewUserDTO(updatedUser);
	}
	
	public void updateUserImage(Long userId, MultipartFile image) throws Exception{
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new UserNotFoundException());
	    if (image == null || image.isEmpty()) {
	        throw new IllegalArgumentException(Messages.EMPTY_IMAGE);
	    }
	    try {
	        String imageUrl = imageService.uploadImage(image);
	        user.setImage(imageUrl);
	        userRepository.save(user);
	    } catch (Exception e) {
	        throw new RuntimeException(Messages.UPLOAD_IMAGE_FAILED + e.getMessage(), e);
	    }
	}
	
	public ViewUserResponseDTO findUserById(Long userId) {
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new UserNotFoundException());
			return userMapper.toViewUserDTO(user);	
	}
	
	public AdminViewUserResponseDTO adminFindUserById(Long userId) {
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new UserNotFoundException());
			return userMapper.toAdminViewUserDTO(user);	
	}
	
	public Page<AdminViewUserResponseDTO> findAllUsers(int page, int size) {
	    Pageable pageable = PageRequest.of(page, size);
	    Page<User> usersPage = userRepository.findAll(pageable);

	    return usersPage.map(userMapper::toAdminViewUserDTO);
	}
	
	public void deleteUser(Long userId) {
		User user = userRepository.findById(userId)
			    .orElseThrow(() -> new UserNotFoundException());
		userRepository.delete(user);
	}
	
	public Page<AdminViewUserResponseDTO> getUsersByDepartmentId(Long departmentId, int page, int size) {
		departmentRepository.findById(departmentId)
				 .orElseThrow(() -> new DepartmentNotFoundException());
		Pageable pageable = PageRequest.of(page, size);
		Page<User> usersPage = userRepository.findAllByDepartment_DepartmentId(departmentId, pageable);
		return usersPage.map(userMapper::toAdminViewUserDTO);

	}

	public List<Role> getAllRoles(){
		return Arrays.asList(Role.values());
	}
}
