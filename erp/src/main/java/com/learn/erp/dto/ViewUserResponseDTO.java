package com.learn.erp.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.learn.erp.model.Department;
import com.learn.erp.model.User.Gender;
import com.learn.erp.model.User.Role;

import lombok.Data;

@Data
public class ViewUserResponseDTO {

    private String username;
    private String email;
    private String fullName;
    private String phone;
    private String image;
    private Role role;
    private Gender gender;
    private String department;
    private LocalDate birthDate;
    private EmployeeDetailsResponseDTO employeeDetails;

}
