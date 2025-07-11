package com.learn.erp.controller;

import com.learn.erp.dto.InventoryLogResponseDTO;
import com.learn.erp.service.InventoryLogService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
