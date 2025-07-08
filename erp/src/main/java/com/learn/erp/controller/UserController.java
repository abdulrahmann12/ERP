package com.learn.erp.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.learn.erp.config.Messages;
import com.learn.erp.dto.AdminUpdateUserRequestDTO;
import com.learn.erp.dto.AdminViewUserResponseDTO;
import com.learn.erp.dto.BasicResponse;
import com.learn.erp.dto.UserUpdateRequestDTO;
import com.learn.erp.dto.ViewUserResponseDTO;
import com.learn.erp.model.User;
import com.learn.erp.model.User.Role;
import com.learn.erp.service.UserService;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "User Controller", description = "API for managing user profiles and account settings.")
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
	
	private final UserService userService;
	
	@PutMapping("/admin/{userId}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<BasicResponse> adminUpdateUser(@PathVariable Long userId, @RequestBody AdminUpdateUserRequestDTO dto){
		AdminViewUserResponseDTO response = userService.adminUpdateUser(userId, dto);
		return ResponseEntity.ok(new BasicResponse(Messages.UPDATE_USER, response));
	}
	
	@PutMapping
	@PreAuthorize("isAuthenticated()")
	public ResponseEntity<BasicResponse> updateUser(
			@AuthenticationPrincipal User user,
			@RequestBody UserUpdateRequestDTO dto,
			@RequestHeader("Authorization") String authHeader
			){
		Long userId = user.getId();
		String token = authHeader.replace("Bearer ", "");
		ViewUserResponseDTO response = userService.updateUser(userId, dto, token);
		return ResponseEntity.ok(new BasicResponse(Messages.UPDATE_USER, response));
	}
	
		@PutMapping("/image")
		@PreAuthorize("isAuthenticated()")
		public ResponseEntity<BasicResponse> updateUserImage(@AuthenticationPrincipal User user, @RequestPart("image") MultipartFile image)throws Exception{
		    Long userId= user.getId();
			userService.updateUserImage(userId, image);
		    return ResponseEntity.ok(new BasicResponse(Messages.USER_UPDATE_IMAGE));
		}
	
	   @GetMapping("/admin/view/{userId}")
	   @PreAuthorize("hasRole('ADMIN')")
	    public ResponseEntity<AdminViewUserResponseDTO> adminFindUserById(@PathVariable Long userId) {
	        return ResponseEntity.ok(userService.adminFindUserById(userId));
	    }
	
		@GetMapping("/profile")
		@PreAuthorize("isAuthenticated()")
		public ResponseEntity<ViewUserResponseDTO> getUserById(@AuthenticationPrincipal User user){
			Long userId = user.getId();
			return ResponseEntity.ok(userService.findUserById(userId));
		}
		
	    @GetMapping
	    @PreAuthorize("hasRole('ADMIN')")
	    public ResponseEntity<Page<AdminViewUserResponseDTO>> getAllUsers(
	            @RequestParam(defaultValue = "0") int page,
	            @RequestParam(defaultValue = "10") int size) {
	        return ResponseEntity.ok(userService.findAllUsers(page, size));
	    }
	    
		@DeleteMapping("/{userId}")
		@PreAuthorize("hasRole('ADMIN')")
		public ResponseEntity<BasicResponse> deleteUser(@PathVariable Long userId){
		    userService.deleteUser(userId);
		    return ResponseEntity.ok(new BasicResponse(Messages.DELETE_USER));
		}
		
	    @GetMapping("/user-roles")
	    @PreAuthorize("hasRole('ADMIN')")
	    public List<Role> getAllRoles() {
	        return userService.getAllRoles();
	    }
}
