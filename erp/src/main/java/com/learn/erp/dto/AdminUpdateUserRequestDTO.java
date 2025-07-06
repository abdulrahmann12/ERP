package com.learn.erp.dto;

import com.learn.erp.model.User;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AdminUpdateUserRequestDTO {

	@Email(message = "Invalid email format")
	@NotBlank(message = "Email is required")
    private String email;
	
	@NotBlank(message = "Full name is required")
    private String fullName;
	
	@NotBlank(message = "Phone is required")
    private String phone;
	    
    @NotBlank(message = "Role is required")
    private User.Role role;
    
    private Long departmentId;
    
    private Boolean active;
}
