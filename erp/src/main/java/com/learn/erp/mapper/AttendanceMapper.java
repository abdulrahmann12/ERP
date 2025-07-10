package com.learn.erp.mapper;

import org.mapstruct.Mapper;

import com.learn.erp.dto.AttendanceResponseDTO;
import com.learn.erp.model.Attendance;

@Mapper(componentModel = "spring", uses = UserMapper.class)
public interface AttendanceMapper {

	AttendanceResponseDTO toDTO(Attendance attendance);
}
