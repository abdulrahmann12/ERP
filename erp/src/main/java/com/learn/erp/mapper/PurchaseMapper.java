package com.learn.erp.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.learn.erp.dto.*;
import com.learn.erp.model.*;

import java.util.List;

@Mapper(componentModel = "spring", uses = {UserMapper.class, SupplierMapper.class, ProductMapper.class})
public interface PurchaseMapper {

    PurchaseResponseDTO toDTO(Purchase purchase);

    @Mapping(target = "supplier.supplierId", source = "supplierId")
    @Mapping(target = "items", ignore = true) 
    Purchase toEntity(PurchaseCreateDTO dto);

    PurchaseItemResponseDTO toDTO(PurchaseItem item);

    @Mapping(target = "product.productId", source = "productId")
    @Mapping(target = "purchase", ignore = true) 
    PurchaseItem toEntity(PurchaseItemCreateDTO dto);

    List<PurchaseItem> toEntity(List<PurchaseItemCreateDTO> dtoList);

    List<PurchaseItemResponseDTO> toDTO(List<PurchaseItem> items);
}
