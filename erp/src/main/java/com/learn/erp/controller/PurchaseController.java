package com.learn.erp.controller;

import com.learn.erp.config.Messages;
import com.learn.erp.dto.BasicResponse;
import com.learn.erp.dto.PurchaseCreateDTO;
import com.learn.erp.dto.PurchaseResponseDTO;
import com.learn.erp.dto.SupplierPurchaseReportDTO;
import com.learn.erp.model.User;
import com.learn.erp.service.PurchaseService;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@Tag(
	    name = "Purchase Controller",
	    description = "API for managing purchases, including creating purchase orders, viewing purchase history, and generating reports or PDFs."
	)
@RestController
@RequestMapping("/api/purchases")
@RequiredArgsConstructor
public class PurchaseController {

	private final PurchaseService purchaseService;
	
    @PostMapping
    @PreAuthorize("hasRole('PURCHASING_OFFICER')")
    public ResponseEntity<BasicResponse> createPurchase(
            @RequestBody PurchaseCreateDTO dto,
            @AuthenticationPrincipal User user
    ) {
        PurchaseResponseDTO created = purchaseService.createPurchase(user.getId(), dto);
        return ResponseEntity.ok(new BasicResponse(Messages.ADD_PURCHASE, created));
    }
    
    @GetMapping
    @PreAuthorize("hasRole('PURCHASING_OFFICER')")
    public ResponseEntity<Page<PurchaseResponseDTO>> getAllPurchases(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Page<PurchaseResponseDTO> purchases = purchaseService.getAllPurchases(page, size);
        return ResponseEntity.ok(purchases);
    }
    
    @GetMapping("/{purchaseId}")
    @PreAuthorize("hasRole('PURCHASING_OFFICER')")
    public ResponseEntity<PurchaseResponseDTO> getPurchaseById(@PathVariable Long purchaseId) {
        PurchaseResponseDTO purchase = purchaseService.getPurchaseById(purchaseId);
        return ResponseEntity.ok(purchase);
    }
    
    @GetMapping("/user/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<PurchaseResponseDTO>> getPurchasesByUser(@PathVariable Long userId) {
        List<PurchaseResponseDTO> purchases = purchaseService.getPurchasesByUser(userId);
        return ResponseEntity.ok(purchases);
    }
    
    @GetMapping("/supplier/{supplierId}")
    @PreAuthorize("hasRole('PURCHASING_OFFICER')")
    public ResponseEntity<List<PurchaseResponseDTO>> getPurchasesBySupplier(@PathVariable Long supplierId) {
        List<PurchaseResponseDTO> purchases = purchaseService.getPurchasesBySupplier(supplierId);
        return ResponseEntity.ok(purchases);
    }
    
    @GetMapping("/supplier/{supplierId}/report")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<SupplierPurchaseReportDTO> generateSupplierPurchaseReport(@PathVariable Long supplierId) {
        SupplierPurchaseReportDTO report = purchaseService.generateSupplierPurchaseReport(supplierId);
        return ResponseEntity.ok(report);
    }
    
    @GetMapping("/supplier/{supplierId}/total-purchase")
    @PreAuthorize("hasRole('ADMIN') or hasRole('PURCHASING_OFFICER')")
    public ResponseEntity<BigDecimal> getSupplierTotalSales(@PathVariable Long supplierId) {
        BigDecimal total = purchaseService.getSupplierTotalSales(supplierId);
        return ResponseEntity.ok(total);
    }
    
    @GetMapping("/{purchaseId}/pdf")
    @PreAuthorize("hasRole('ADMIN') or hasRole('PURCHASING_OFFICER')")
    public ResponseEntity<byte[]> downloadPurchasePdf(@PathVariable Long purchaseId) {
        byte[] pdfBytes = purchaseService.generatePurchasePdfById(purchaseId);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=purchase-invoice-" + purchaseId + ".pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdfBytes);
    }
}