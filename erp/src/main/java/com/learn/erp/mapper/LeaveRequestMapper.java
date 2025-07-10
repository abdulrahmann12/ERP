package com.learn.erp.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.learn.erp.dto.LeaveRequestCreateDTO;
import com.learn.erp.dto.LeaveRequestResponseDTO;
import com.learn.erp.dto.UserLeaveRequestResponseDTO;
import com.learn.erp.model.LeaveRequest;

@Mapper(componentModel = "spring", uses = UserMapper.class)
public interface LeaveRequestMapper {

	LeaveRequestResponseDTO toHRDTO(LeaveRequest dto);	
	
	UserLeaveRequestResponseDTO toUserDTO(LeaveRequest dto);
	
	@Mapping(target = "user.id", ignore = true)
	@Mapping(target = "createdAt", ignore = true)
	@Mapping(target = "requestId", ignore = true)
	@Mapping(target = "status", ignore = true)
	LeaveRequest toEntity(LeaveRequestCreateDTO dto);	
}
