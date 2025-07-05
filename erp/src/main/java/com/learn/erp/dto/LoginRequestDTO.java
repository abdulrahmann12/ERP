package com.learn.erp.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequestDTO {

	@Email(message = "Invalid email format")
    @NotBlank(message = "email is required")
	private String email;
    
    @NotBlank(message = "password is required")
    private String password;
}