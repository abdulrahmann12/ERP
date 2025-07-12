package com.learn.erp.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.learn.erp.dto.BonusCreateDTO;
import com.learn.erp.dto.BonusResponseDTO;
import com.learn.erp.model.Bonus;

@Mapper(componentModel = "spring")
public interface BonusMapper {

    @Mapping(target = "userFullName", source = "user.fullName")
    BonusResponseDTO toDTO(Bonus bonus);

    @Mapping(target = "bonusId", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    Bonus toEntity(BonusCreateDTO dto);
}
