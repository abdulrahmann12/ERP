package com.learn.erp.controller;

import com.learn.erp.config.Messages;
import com.learn.erp.dto.BasicResponse;
import com.learn.erp.dto.CustomerCreateDTO;
import com.learn.erp.dto.CustomerDTO;
import com.learn.erp.dto.CustomerUpdateDTO;
import com.learn.erp.service.CustomerService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping
    @PreAuthorize("hasRole('SALES_EMPLOYEE')")
    public ResponseEntity<BasicResponse> createCustomer(@RequestBody CustomerCreateDTO dto) {
        CustomerDTO created = customerService.createCustomer(dto);
        return ResponseEntity.ok(new BasicResponse(Messages.ADD_CUSTOMER, created));
    }

    @PutMapping("/{customerId}")
    @PreAuthorize("hasRole('SALES_EMPLOYEE')")
    public ResponseEntity<BasicResponse> updateCustomer(
            @PathVariable Long customerId,
            @RequestBody CustomerUpdateDTO dto
    ) {
        CustomerDTO updated = customerService.updateCustomer(customerId, dto);
        return ResponseEntity.ok(new BasicResponse(Messages.UPDATE_CUSTOMER, updated));
    }

    @GetMapping("/{customerId}")
    @PreAuthorize("hasRole('SALES_EMPLOYEE')")
    public ResponseEntity<CustomerDTO> getCustomer(@PathVariable Long customerId) {
        CustomerDTO customer = customerService.getCustomer(customerId);
        return ResponseEntity.ok(customer);
    }

    @GetMapping
    @PreAuthorize("hasRole('SALES_EMPLOYEE')")
    public ResponseEntity<List<CustomerDTO>> getAllCustomers() {
        List<CustomerDTO> customers = customerService.getAllCustomer();
        return ResponseEntity.ok(customers);
    }

    @DeleteMapping("/{customerId}")
    @PreAuthorize("hasRole('SALES_EMPLOYEE')")
    public ResponseEntity<BasicResponse> deleteCustomer(@PathVariable Long customerId) {
        customerService.deleteCustomer(customerId);
        return ResponseEntity.ok(new BasicResponse(Messages.DELETE_CUSTOMER, null));
    }
}