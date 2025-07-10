package com.learn.erp.dto;

import com.learn.erp.model.User.Gender;
import com.learn.erp.model.User.Role;

import lombok.Data;

@Data
public class HRViewUserResponseDTO {

	private Long userId;
    private String username;
    private String email;
    private String fullName;
    private String phone;
    private Role role;
    private Gender gender;
    private String department;
    private EmployeeDetailsResponseDTO employeeDetails;
}
