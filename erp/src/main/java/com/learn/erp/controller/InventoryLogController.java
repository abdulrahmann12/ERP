package com.learn.erp.controller;

import com.learn.erp.dto.InventoryLogResponseDTO;
import com.learn.erp.service.InventoryLogService;

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

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<InventoryLogResponseDTO>> getAllLogs() {
        List<InventoryLogResponseDTO> logs = inventoryLogService.getAllLogs();
        return ResponseEntity.ok(logs);
    }

    @GetMapping("/product/{productId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<InventoryLogResponseDTO>> getLogsByProduct(@PathVariable Long productId) {
        List<InventoryLogResponseDTO> logs = inventoryLogService.getLogsByProductId(productId);
        return ResponseEntity.ok(logs);
    }
}
