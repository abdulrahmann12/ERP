package com.learn.erp.dto;

import java.time.LocalDate;
import com.learn.erp.model.User.Gender;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
		
	@NotBlank(message = "Phone is required")
    private String phone;
	
	@NotNull(message = "Gender is required")
    private Gender gender;
    
	private LocalDate birthDate;
}
