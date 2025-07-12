package com.learn.erp.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.learn.erp.config.Messages;
import com.learn.erp.dto.SupplierCreateDTO;
import com.learn.erp.dto.SupplierDTO;
import com.learn.erp.dto.SupplierUpdateDTO;
import com.learn.erp.exception.SupplierNotFoundException;
import com.learn.erp.exception.DuplicateResourceException;
import com.learn.erp.mapper.SupplierMapper;
import com.learn.erp.model.Supplier;
import com.learn.erp.repository.SupplierRepository;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Service
@Validated
@RequiredArgsConstructor
public class SupplierService {

    private final SupplierRepository supplierRepository;
    private final SupplierMapper supplierMapper;

    public SupplierDTO createSupplier(@Valid SupplierCreateDTO dto) {
        supplierRepository.findByEmail(dto.getEmail()).ifPresent(existing -> {
            throw new DuplicateResourceException(Messages.SUPPLIER_ALREADY_EXISTS);
        });

        Supplier supplier = supplierMapper.toEntity(dto);
        Supplier savedSupplier = supplierRepository.save(supplier);
        return supplierMapper.toDTO(savedSupplier);
    }

    public SupplierDTO updateSupplier(Long supplierId, @Valid SupplierUpdateDTO dto) {
        Supplier existingSupplier = supplierRepository.findById(supplierId)
                .orElseThrow(SupplierNotFoundException::new);

        supplierRepository.findByEmail(dto.getEmail()).ifPresent(existing -> {
            if (!existing.getSupplierId().equals(supplierId)) {
                throw new DuplicateResourceException(Messages.SUPPLIER_ALREADY_EXISTS);
            }
        });

        existingSupplier.setName(dto.getName());
        existingSupplier.setPhone(dto.getPhone());
        existingSupplier.setEmail(dto.getEmail());
        existingSupplier.setAddress(dto.getAddress());

        Supplier updatedSupplier = supplierRepository.save(existingSupplier);
        return supplierMapper.toDTO(updatedSupplier);
    }

    public SupplierDTO getSupplier(Long supplierId) {
        Supplier supplier = supplierRepository.findById(supplierId)
                .orElseThrow(SupplierNotFoundException::new);
        return supplierMapper.toDTO(supplier);
    }

    public List<SupplierDTO> getAllSupplier() {
        return supplierRepository.findAll()
                .stream()
                .map(supplierMapper::toDTO)
                .collect(Collectors.toList());
    }

    public void deleteSupplier(Long supplierId) {
        Supplier supplier = supplierRepository.findById(supplierId)
                .orElseThrow(SupplierNotFoundException::new);
        supplierRepository.delete(supplier);
    }
}