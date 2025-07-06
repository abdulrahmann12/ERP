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
	LeaveRequest toEntity(LeaveRequestCreateDTO dto);	
}
