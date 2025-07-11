package com.learn.erp.dto;

import com.learn.erp.model.InventoryLog.ActionType;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class InventoryLogResponseDTO {

    private Long logId;
    private ProductResponseDTO product;
    private Integer quantityBefore;
    private Integer quantityAfter;
    private ActionType actionType;
    private String note;
    private LocalDateTime createdAt;
    private String userFullName;
}
