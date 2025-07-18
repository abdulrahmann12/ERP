package com.learn.erp.controller;

import com.learn.erp.dto.InventoryLogResponseDTO;
import com.learn.erp.service.InventoryLogService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(
	    name = "Inventory Log Controller",
	    description = "API for viewing inventory logs, including product-specific history."
	)
@RestController
@RequestMapping("/api/inventory-logs")
@RequiredArgsConstructor
public class InventoryLogController {

    private final InventoryLogService inventoryLogService;

    @Operation(
    		summary = "Get all inventory logs",
    		description = "Fetches the complete history of inventory logs."
    	)
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<InventoryLogResponseDTO>> getAllLogs() {
        List<InventoryLogResponseDTO> logs = inventoryLogService.getAllLogs();
        return ResponseEntity.ok(logs);
    }

    @Operation(
    		summary = "Get inventory logs by product",
    		description = "Fetches inventory log entries for a specific product."
    )
    @GetMapping("/product/{productId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<InventoryLogResponseDTO>> getLogsByProduct(@PathVariable Long productId) {
        List<InventoryLogResponseDTO> logs = inventoryLogService.getLogsByProductId(productId);
        return ResponseEntity.ok(logs);
    }
}