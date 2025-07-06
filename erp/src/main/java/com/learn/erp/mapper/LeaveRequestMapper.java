package com.learn.erp.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.learn.erp.dto.LeaveRequestCreateDTO;
import com.learn.erp.dto.LeaveRequestResponseDTO;
import com.learn.erp.model.LeaveRequest;

@Mapper(componentModel = "spring", uses = UserMapper.class)
public interface LeaveRequestMapper {

	LeaveRequestResponseDTO toDTO(LeaveRequest dto);	
	
	@Mapping(target = "user.id", source = "userId")
	@Mapping(target = "createdAt", ignore = true)
	@Mapping(target = "requestId", ignore = true)
	@Mapping(target = "status", ignore = true)
	LeaveRequest toEntity(LeaveRequestCreateDTO dto);	
}
