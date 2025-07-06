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
	@Mapping(target = "attendanceId", ignore = true)
	@Mapping(target = "checkOut", ignore = true)
	@Mapping(target = "workingHours", ignore = true)
	@Mapping(target = "status", ignore = true)
	Attendance toEntity(AttendanceCheckInDTO dto);
	
	@Mapping(target = "attendanceId", ignore = true)
	@Mapping(target = "checkIn", ignore = true)
	@Mapping(target = "workingHours", ignore = true)
	@Mapping(target = "status", ignore = true)
	@Mapping(target = "user.id", ignore = true)
	@Mapping(target = "date", ignore = true)
	Attendance toEntity(AttendanceCheckOutDTO dto);
}
