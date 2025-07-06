package com.learn.erp.mapper;

import org.mapstruct.Mapper;

import com.learn.erp.dto.CustomerCreateDTO;
import com.learn.erp.dto.CustomerDTO;
import com.learn.erp.model.Customer;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

	CustomerDTO toDTO(Customer customer);

	Customer toEntity(CustomerCreateDTO dto);
}
