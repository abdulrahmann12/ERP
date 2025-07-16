package com.learn.erp.controller;

import com.learn.erp.config.Messages;
import com.learn.erp.dto.BasicResponse;
import com.learn.erp.dto.CustomerSalesReportDTO;
import com.learn.erp.dto.SaleCreateDTO;
import com.learn.erp.dto.SaleResponseDTO;
import com.learn.erp.model.User;
import com.learn.erp.service.SaleService;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/sales")
@RequiredArgsConstructor
public class SaleController {

    private final SaleService saleService;

    @PostMapping
    @PreAuthorize("hasRole('SALES_EMPLOYEE')")
    public ResponseEntity<BasicResponse> createSale(@RequestBody SaleCreateDTO dto, @AuthenticationPrincipal User user) {
        SaleResponseDTO created = saleService.createSale(user.getId(), dto);
        return ResponseEntity.ok(new BasicResponse(Messages.ADD_SALE, created));
    }

    @GetMapping
    @PreAuthorize("hasRole('SALES_EMPLOYEE')")
    public ResponseEntity<Page<SaleResponseDTO>> getAllSales(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Page<SaleResponseDTO> sales = saleService.getAllSales(page, size);
        return ResponseEntity.ok(sales);
    }

    @GetMapping("/{saleId}")
    @PreAuthorize("hasRole('SALES_EMPLOYEE')")
    public ResponseEntity<SaleResponseDTO> getSaleById(@PathVariable Long saleId) {
        SaleResponseDTO sale = saleService.getSaleById(saleId);
        return ResponseEntity.ok(sale);
    }

    @GetMapping("/user/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<SaleResponseDTO>> getSalesByUser(@PathVariable Long userId) {
        List<SaleResponseDTO> sales = saleService.getSalesByUser(userId);
        return ResponseEntity.ok(sales);
    }

    @GetMapping("/customer/{customerId}")
    @PreAuthorize("hasRole('SALES_EMPLOYEE')")
    public ResponseEntity<List<SaleResponseDTO>> getSalesByCustomer(@PathVariable Long customerId) {
        List<SaleResponseDTO> sales = saleService.getSalesByCustomer(customerId);
        return ResponseEntity.ok(sales);
    }

    @GetMapping("/customer/{customerId}/report")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CustomerSalesReportDTO> generateCustomerSalesReport(@PathVariable Long customerId) {
        CustomerSalesReportDTO report = saleService.generateCustomerSalesReport(customerId);
        return ResponseEntity.ok(report);
    }
    
    @GetMapping("/customer/{customerId}/total-sales")
    @PreAuthorize("hasRole('ADMIN') or hasRole('SALES_EMPLOYEE')")
    public ResponseEntity<BigDecimal> getCustomerTotalSales(@PathVariable Long customerId) {
        BigDecimal totalSales = saleService.getCustomerTotalSales(customerId);
        return ResponseEntity.ok(totalSales);
    }
     
    @GetMapping("/{saleId}/pdf")
    @PreAuthorize("hasRole('ADMIN') or hasRole('SALES_EMPLOYEE')")
    public ResponseEntity<byte[]> downloadSalePdf(@PathVariable Long saleId) {
        byte[] pdfBytes = saleService.generateSalePdfById(saleId);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=invoice-" + saleId + ".pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdfBytes);
    }
}
