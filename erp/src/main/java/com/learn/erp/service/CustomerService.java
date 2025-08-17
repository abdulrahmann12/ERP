package com.learn.erp.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.learn.erp.config.Messages;
import com.learn.erp.dto.CustomerCreateDTO;
import com.learn.erp.dto.CustomerDTO;
import com.learn.erp.dto.CustomerUpdateDTO;
import com.learn.erp.exception.CustomerNotFoundException;
import com.learn.erp.exception.DuplicateResourceException;
import com.learn.erp.mapper.CustomerMapper;
import com.learn.erp.model.Customer;
import com.learn.erp.repository.CustomerRepository;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Service
@Validated
@RequiredArgsConstructor
public class CustomerService {

	private final CustomerRepository customerRepository;
	private final CustomerMapper customerMapper;
	
	@Transactional
	public CustomerDTO createCustomer(@Valid CustomerCreateDTO dto) {
        customerRepository.findByEmail(dto.getEmail()).ifPresent(c -> {
            throw new DuplicateResourceException(Messages.CUSTOMER_ALREADY_EXISTS);
        });
        Customer customer = customerMapper.toEntity(dto);
        Customer savedCustomer= customerRepository.save(customer);
        return customerMapper.toDTO(savedCustomer);
	}
	
	@Transactional
	public CustomerDTO updateCustomer(Long customerId, @Valid CustomerUpdateDTO dto) {
		Customer existingCustomer = customerRepository.findById(customerId)
				.orElseThrow(() -> new CustomerNotFoundException());
		
		customerRepository.findByEmail(dto.getEmail()).ifPresent(c -> {
		    if (!c.getCustomerId().equals(customerId)) {
		        throw new DuplicateResourceException(Messages.CUSTOMER_ALREADY_EXISTS);
		    }
		});
        existingCustomer.setAddress(dto.getAddress());
        existingCustomer.setEmail(dto.getEmail());
        existingCustomer.setName(dto.getName());
        existingCustomer.setPhone(dto.getPhone());
        
        Customer updateCustomer= customerRepository.save(existingCustomer);
        return customerMapper.toDTO(updateCustomer);
	}
	
	public CustomerDTO getCustomer(Long customerId) {
		Customer customer = customerRepository.findById(customerId)
				.orElseThrow(() -> new CustomerNotFoundException());
		return customerMapper.toDTO(customer);
	}
	
	public List<CustomerDTO> getAllCustomer() {
		List<Customer> customers = customerRepository.findAll();
		return customers.stream()
				.map(customerMapper::toDTO)
				.collect(Collectors.toList());
	}

	@Transactional
	public void deleteCustomer(Long customerId) {
		Customer customer = customerRepository.findById(customerId)
				.orElseThrow(() -> new CustomerNotFoundException());
		customerRepository.delete(customer);
	}
}