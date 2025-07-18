package com.learn.erp.service;

import com.learn.erp.dto.InventoryLogResponseDTO;
import com.learn.erp.mapper.InventoryLogMapper;
import com.learn.erp.repository.InventoryLogRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InventoryLogService {

    private final InventoryLogRepository inventoryLogRepository;
    private final InventoryLogMapper inventoryLogMapper;

    public List<InventoryLogResponseDTO> getAllLogs() {
        return inventoryLogRepository.findAll().stream()
                .map(inventoryLogMapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<InventoryLogResponseDTO> getLogsByProductId(Long productId) {
        return inventoryLogRepository.findByProduct_ProductId(productId).stream()
                .map(inventoryLogMapper::toDTO)
                .collect(Collectors.toList());
    }
}
