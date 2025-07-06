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
	
	@Mapping(target = "department.id", source = "departmentId")
	User toEntity(AdminCreateUserRequestDTO dto);
	
	@Mapping(target = "department.id", source = "departmentId")
	User toEntity(AdminUpdateUserRequestDTO dto);

	User toEntity(UserUpdateRequestDTO dto);	
}
