package com.learn.erp.controller;

import com.learn.erp.config.Messages;
import com.learn.erp.dto.BasicResponse;
import com.learn.erp.dto.SupplierDTO;
import com.learn.erp.dto.SupplierUpdateDTO;
import com.learn.erp.dto.SupplierCreateDTO;
import com.learn.erp.service.SupplierService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/suppliers")
@RequiredArgsConstructor
public class SupplierController {

    private final SupplierService supplierService;

    @PostMapping
    @PreAuthorize("hasRole('PURCHASING_OFFICER')")
    public ResponseEntity<BasicResponse> createSupplier(@RequestBody SupplierCreateDTO dto) {
        SupplierDTO created = supplierService.createSupplier(dto);
        return ResponseEntity.ok(new BasicResponse(Messages.ADD_SUPPLIER, created));
    }

    @PutMapping("/{supplierId}")
    @PreAuthorize("hasRole('PURCHASING_OFFICER')")
    public ResponseEntity<BasicResponse> updateSupplier(
            @PathVariable Long supplierId,
            @RequestBody SupplierUpdateDTO dto
    ) {
        SupplierDTO updated = supplierService.updateSupplier(supplierId, dto);
        return ResponseEntity.ok(new BasicResponse(Messages.UPDATE_SUPPLIER, updated));
    }

    @GetMapping("/{supplierId}")
    @PreAuthorize("hasRole('PURCHASING_OFFICER')")
    public ResponseEntity<SupplierDTO> getSupplier(@PathVariable Long supplierId) {
        SupplierDTO supplier = supplierService.getSupplier(supplierId);
        return ResponseEntity.ok(supplier);
    }

    @GetMapping
    @PreAuthorize("hasRole('PURCHASING_OFFICER')")
    public ResponseEntity<List<SupplierDTO>> getAllSuppliers() {
        List<SupplierDTO> suppliers = supplierService.getAllSupplier();
        return ResponseEntity.ok(suppliers);
    }

    @DeleteMapping("/{supplierId}")
    @PreAuthorize("hasRole('PURCHASING_OFFICER')")
    public ResponseEntity<BasicResponse> deleteSupplier(@PathVariable Long supplierId) {
    	supplierService.deleteSupplier(supplierId);
        return ResponseEntity.ok(new BasicResponse(Messages.DELETE_SUPPLIER, null));
    }
}