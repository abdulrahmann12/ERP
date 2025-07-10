package com.learn.erp.mapper;

import java.util.List;

import org.mapstruct.*;

import com.learn.erp.dto.AdminCreateUserRequestDTO;
import com.learn.erp.dto.AdminViewUserResponseDTO;
import com.learn.erp.dto.HRViewUserResponseDTO;
import com.learn.erp.dto.UserSummaryDTO;
import com.learn.erp.dto.ViewUserResponseDTO;
import com.learn.erp.model.User;

@Mapper(componentModel = "spring", uses = EmployeeMapper.class)
public interface UserMapper {

	@Mapping(target = "department", source = "department.name")
	@Mapping(target = "userId", source = "id")
	@Mapping(target = "username", expression = "java(user.getUsernameField())")
	AdminViewUserResponseDTO toAdminViewUserDTO(User user);
	
	@Mapping(target = "department", source = "department.name")
	@Mapping(target = "userId", source = "id")
	@Mapping(target = "username", expression = "java(user.getUsernameField())")
	HRViewUserResponseDTO toAHRViewUserDTO(User user);
	
	@Mapping(target = "username", expression = "java(user.getUsernameField())")
	List<AdminViewUserResponseDTO> toAdminViewUserDTOS(List<User> user);

	
	@Mapping(target = "department", source = "department.name")
	@Mapping(target = "userId", source = "id")
	@Mapping(target = "username", expression = "java(user.getUsernameField())")
	UserSummaryDTO toSummaryDTO(User user);

	
	@Mapping(target = "department", source = "department.name")
	@Mapping(target = "userId", source = "id")
	@Mapping(target = "username", expression = "java(user.getUsernameField())")
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


}
