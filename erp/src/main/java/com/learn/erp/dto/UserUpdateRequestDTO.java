package com.learn.erp.dto;

import java.time.LocalDate;

import com.learn.erp.model.User;
import com.learn.erp.model.User.Gender;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserUpdateRequestDTO {

	@Email(message = "Invalid email format")
	@NotBlank(message = "Email is required")
    private String email;

	@NotBlank(message = "Username name is required")
    private String username;
	
	@NotBlank(message = "Full name is required")
    private String fullName;
	
    private String image;
	
	@NotBlank(message = "Phone is required")
    private String phone;
	
	@NotBlank(message = "Gender is required")
    private Gender gender;
    
	private LocalDate birthDate;
}
