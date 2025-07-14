package com.learn.erp.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.learn.erp.config.Messages;
import com.learn.erp.dto.PurchaseCreateDTO;
import com.learn.erp.dto.PurchaseItemCreateDTO;
import com.learn.erp.dto.PurchaseResponseDTO;
import com.learn.erp.dto.SupplierPurchaseReportDTO;
import com.learn.erp.exception.ProductNotFoundException;
import com.learn.erp.exception.PurchaseNotFoundException;
import com.learn.erp.exception.SupplierNotFoundException;
import com.learn.erp.exception.UnauthorizedActionException;
import com.learn.erp.exception.UserNotFoundException;
import com.learn.erp.mapper.PurchaseMapper;
import com.learn.erp.mapper.SupplierMapper;
import com.learn.erp.model.Product;
import com.learn.erp.model.Purchase;
import com.learn.erp.model.PurchaseItem;
import com.learn.erp.model.Supplier;
import com.learn.erp.model.User;
import com.learn.erp.model.User.Role;
import com.learn.erp.repository.ProductRepository;
import com.learn.erp.repository.PurchaseRepository;
import com.learn.erp.repository.SupplierRepository;
import com.learn.erp.repository.UserRepository;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Service
@Validated
@RequiredArgsConstructor
public class PurchaseService {

	private final PurchaseRepository purchaseRepository;
	private final UserRepository userRepository;
	private final SupplierRepository supplierRepository;
	private final ProductRepository productRepository;
	private final PurchaseMapper purchaseMapper;
	private final SupplierMapper supplierMapper;
	
	public PurchaseResponseDTO createPurchase(Long userId, @Valid PurchaseCreateDTO dto) {
        User user = userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);

        Supplier supplier = supplierRepository.findById(dto.getSupplierId())
                .orElseThrow(SupplierNotFoundException::new);
        
        Purchase purchase = Purchase.builder()
        		.user(user)
        		.supplier(supplier)
        		.notes(dto.getNotes())
        		.build();
        
        BigDecimal totalAmount = BigDecimal.ZERO;
        
        for(PurchaseItemCreateDTO itemDTO : dto.getItems()) {
            Product product = productRepository.findById(itemDTO.getProductId())
                    .orElseThrow(ProductNotFoundException::new);
            
            BigDecimal price = product.getPrice();
            BigDecimal lineTotal = price.multiply(BigDecimal.valueOf(itemDTO.getQuantity()));
            totalAmount = totalAmount.add(lineTotal);
            
            product.setStock(product.getStock() + itemDTO.getQuantity());
            productRepository.save(product);
            
            PurchaseItem item = PurchaseItem.builder()
            		.product(product)
            		.price(lineTotal)
            		.purchase(purchase)
            		.quantity(itemDTO.getQuantity())
            		.build();
            
            purchase.getItems().add(item);
        }
        purchase.setTotalAmount(totalAmount);
        
        Purchase saved = purchaseRepository.save(purchase);
        return purchaseMapper.toDTO(saved);
	}
	
	public Page<PurchaseResponseDTO> getAllPurchases(int page, int size){
		Pageable pageable = PageRequest.of(page, size);
		return purchaseRepository.findAll(pageable).map(purchaseMapper::toDTO);
	}
	
	public List<PurchaseResponseDTO> getPurchasesBySupplier(Long supplierId){
        Supplier supplier = supplierRepository.findById(supplierId)
                .orElseThrow(SupplierNotFoundException::new);
        
        List<Purchase> purchases = purchaseRepository.findBySupplier(supplier);
        return purchases.stream()    
        		.map(purchaseMapper::toDTO)
                .collect(Collectors.toList());
	}
	
    public PurchaseResponseDTO getPurchaseById(Long purchaseId) {
        Purchase purchase = purchaseRepository.findById(purchaseId)
                .orElseThrow(PurchaseNotFoundException::new);
        return purchaseMapper.toDTO(purchase);
    }
    
    public List<PurchaseResponseDTO> getPurchasesByUser(Long userId){
        User user = userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);
    	
        if(user.getRole() != Role.PURCHASING_OFFICER) {
            throw new UnauthorizedActionException(Messages.NOT_PURCHASES_EMPLOYEE);
        }
        List<Purchase> purchases = purchaseRepository.findByUser(user);
        return purchases.stream()    
        		.map(purchaseMapper::toDTO)
                .collect(Collectors.toList());
    }

    public BigDecimal getSupplierTotalSales(Long supplierId) {
        Supplier supplier = supplierRepository.findById(supplierId)
                .orElseThrow(SupplierNotFoundException::new);

        List<Purchase> purchases = purchaseRepository.findBySupplier(supplier);
        return purchases.stream()
                .map(Purchase::getTotalAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public SupplierPurchaseReportDTO generateSupplierPurchaseReport(Long supplierId) {
        Supplier supplier = supplierRepository.findById(supplierId)
                .orElseThrow(SupplierNotFoundException::new);
        
        List<Purchase> purchases = purchaseRepository.findBySupplier(supplier);
        BigDecimal totalAmount = getSupplierTotalSales(supplier.getSupplierId());
        
        return SupplierPurchaseReportDTO.builder()
                .supplier(supplierMapper.toDTO(supplier))
                .totalSales(totalAmount)
                .numberOfOrders(purchases.size())
                .build();
    }
}
