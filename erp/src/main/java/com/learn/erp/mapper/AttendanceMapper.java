package com.learn.erp.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.learn.erp.dto.AttendanceCheckInDTO;
import com.learn.erp.dto.AttendanceCheckOutDTO;
import com.learn.erp.dto.AttendanceResponseDTO;
import com.learn.erp.model.Attendance;

@Mapper(componentModel = "spring", uses = UserMapper.class)
public interface AttendanceMapper {

	AttendanceResponseDTO toDTO(Attendance attendance);
	
	@Mapping(target = "user.id", source = "userId")
	Attendance toEntity(AttendanceCheckInDTO dto);
	
	@Mapping(target = "user.id", source = "userId")
	Attendance toEntity(AttendanceCheckOutDTO dto);
	
}
