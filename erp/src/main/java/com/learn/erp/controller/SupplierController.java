package com.learn.erp.controller;

import com.learn.erp.config.Messages;
import com.learn.erp.dto.BasicResponse;
import com.learn.erp.dto.SupplierDTO;
import com.learn.erp.dto.SupplierUpdateDTO;
import com.learn.erp.dto.SupplierCreateDTO;
import com.learn.erp.service.SupplierService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(
	    name = "Supplier Controller",
	    description = "API for managing suppliers, including adding, updating, viewing, and deleting supplier records."
	)
@RestController
@RequestMapping("/api/suppliers")
@RequiredArgsConstructor
public class SupplierController {

    private final SupplierService supplierService;

    @Operation(
    	    summary = "Create new supplier",
    	    description = "Allows the purchasing officer to add a new supplier to the system"
    	)
    @PostMapping
    @PreAuthorize("hasRole('PURCHASING_OFFICER')")
    public ResponseEntity<BasicResponse> createSupplier(@RequestBody SupplierCreateDTO dto) {
        SupplierDTO created = supplierService.createSupplier(dto);
        return ResponseEntity.ok(new BasicResponse(Messages.ADD_SUPPLIER, created));
    }

    @Operation(
    	    summary = "Update supplier details",
    	    description = "Allows the purchasing officer to update information for an existing supplier"
    	)
    @PutMapping("/{supplierId}")
    @PreAuthorize("hasRole('PURCHASING_OFFICER')")
    public ResponseEntity<BasicResponse> updateSupplier(
            @PathVariable Long supplierId,
            @RequestBody SupplierUpdateDTO dto
    ) {
        SupplierDTO updated = supplierService.updateSupplier(supplierId, dto);
        return ResponseEntity.ok(new BasicResponse(Messages.UPDATE_SUPPLIER, updated));
    }

    @Operation(
    	    summary = "Get supplier by ID",
    	    description = "Retrieves details of a specific supplier by their ID"
    	)
    @GetMapping("/{supplierId}")
    @PreAuthorize("hasRole('PURCHASING_OFFICER')")
    public ResponseEntity<SupplierDTO> getSupplier(@PathVariable Long supplierId) {
        SupplierDTO supplier = supplierService.getSupplier(supplierId);
        return ResponseEntity.ok(supplier);
    }

    @Operation(
    	    summary = "Get all suppliers",
    	    description = "Returns a list of all suppliers in the system"
    	)
    @GetMapping
    @PreAuthorize("hasRole('PURCHASING_OFFICER')")
    public ResponseEntity<List<SupplierDTO>> getAllSuppliers() {
        List<SupplierDTO> suppliers = supplierService.getAllSupplier();
        return ResponseEntity.ok(suppliers);
    }

    @Operation(
    	    summary = "Delete supplier",
    	    description = "Allows the purchasing officer to delete a supplier by their ID"
    	)
    @DeleteMapping("/{supplierId}")
    @PreAuthorize("hasRole('PURCHASING_OFFICER')")
    public ResponseEntity<BasicResponse> deleteSupplier(@PathVariable Long supplierId) {
    	supplierService.deleteSupplier(supplierId);
        return ResponseEntity.ok(new BasicResponse(Messages.DELETE_SUPPLIER, null));
    }
}