package com.learn.erp.mapper;

import org.mapstruct.*;

import com.learn.erp.dto.AdminCreateUserRequestDTO;
import com.learn.erp.dto.AdminUpdateUserRequestDTO;
import com.learn.erp.dto.AdminViewUserResponseDTO;
import com.learn.erp.dto.UserSummaryDTO;
import com.learn.erp.dto.UserUpdateRequestDTO;
import com.learn.erp.dto.ViewUserResponseDTO;
import com.learn.erp.model.User;

@Mapper(componentModel = "spring", uses = EmployeeMapper.class)
public interface UserMapper {

	@Mapping(target = "department", source = "department.name")
	@Mapping(target = "userId", source = "id")
	AdminViewUserResponseDTO toAdminViewUserDTO(User user);

	@Mapping(target = "department", source = "department.name")
	@Mapping(target = "userId", source = "id")
	UserSummaryDTO toSummaryDTO(User user);

	
	@Mapping(target = "department", source = "department.name")
	@Mapping(target = "userId", source = "id")
	ViewUserResponseDTO toViewUserDTO(User user);
	
	@Mapping(target = "department.departmentId", source = "departmentId")
	@Mapping(target = "active", ignore = true)
	@Mapping(target = "birthDate", ignore = true)
	@Mapping(target = "createdAt", ignore = true)
	@Mapping(target = "employeeDetails", ignore = true)
	@Mapping(target = "fullName", ignore = true)
	@Mapping(target = "gender", ignore = true)
	@Mapping(target = "id", ignore = true)
	@Mapping(target = "image", ignore = true)
	@Mapping(target = "phone", ignore = true)
	@Mapping(target = "authorities", ignore = true)
	@Mapping(target = "requestCode", ignore = true)
	User toEntity(AdminCreateUserRequestDTO dto);

	@Mapping(target = "department.departmentId", source = "departmentId")
	@Mapping(target = "id", source = "userId")
	@Mapping(target = "active", ignore = true)
	@Mapping(target = "birthDate", ignore = true)
	@Mapping(target = "createdAt", ignore = true)
	@Mapping(target = "employeeDetails", ignore = true)
	@Mapping(target = "fullName", ignore = true)
	@Mapping(target = "gender", ignore = true)
	@Mapping(target = "image", ignore = true)
	@Mapping(target = "phone", ignore = true)
	@Mapping(target = "authorities", ignore = true)
	@Mapping(target = "password", ignore = true)
	@Mapping(target = "username", ignore = true)
	@Mapping(target = "requestCode", ignore = true)
	User toEntity(AdminUpdateUserRequestDTO dto);

	@Mapping(target = "active", ignore = true)
	@Mapping(target = "department" , ignore = true)
	@Mapping(target = "employeeDetails", ignore = true)
	@Mapping(target = "createdAt", ignore = true)
	@Mapping(target = "authorities", ignore = true)
	@Mapping(target = "role", ignore = true)
	@Mapping(target = "password", ignore = true)
	@Mapping(target = "requestCode", ignore = true)
	@Mapping(target = "id", ignore = true)
	User toEntity(UserUpdateRequestDTO dto);	
}
