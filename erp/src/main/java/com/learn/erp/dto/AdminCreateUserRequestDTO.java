package com.learn.erp.dto;

import com.learn.erp.model.User;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class AdminCreateUserRequestDTO {
	
	@NotBlank(message = "Username is required")
    private String username;
    
	@Email(message = "Invalid email format")
	@NotBlank(message = "Email is required")
    private String email;
    
	@NotBlank(message = "Password is required")
	@Size(min = 6, message = "Password too short")
    private String password;
    
	@NotNull(message = "Role is required")
	private User.Role role;
    
    private Long departmentId;
}
