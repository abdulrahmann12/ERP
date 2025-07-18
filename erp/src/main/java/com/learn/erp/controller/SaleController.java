package com.learn.erp.controller;

import com.learn.erp.config.Messages;
import com.learn.erp.dto.BasicResponse;
import com.learn.erp.dto.CustomerSalesReportDTO;
import com.learn.erp.dto.SaleCreateDTO;
import com.learn.erp.dto.SaleResponseDTO;
import com.learn.erp.model.User;
import com.learn.erp.service.SaleService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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

@Tag(
	    name = "Sale Controller",
	    description = "API for handling sales operations, including creating sales, generating reports, and exporting sales invoices."
	)
@RestController
@RequestMapping("/api/sales")
@RequiredArgsConstructor
public class SaleController {

    private final SaleService saleService;

    @Operation(
    	    summary = "Create a new sale",
    	    description = "Creates a new sale by a sales employee and returns the sale details"
    	)
    @PostMapping
    @PreAuthorize("hasRole('SALES_EMPLOYEE')")
    public ResponseEntity<BasicResponse> createSale(@RequestBody SaleCreateDTO dto, @AuthenticationPrincipal User user) {
        SaleResponseDTO created = saleService.createSale(user.getId(), dto);
        return ResponseEntity.ok(new BasicResponse(Messages.ADD_SALE, created));
    }

    @Operation(
    	    summary = "Get all sales",
    	    description = "Retrieves a paginated list of all sales made in the system"
    	)
    @GetMapping
    @PreAuthorize("hasRole('SALES_EMPLOYEE')")
    public ResponseEntity<Page<SaleResponseDTO>> getAllSales(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Page<SaleResponseDTO> sales = saleService.getAllSales(page, size);
        return ResponseEntity.ok(sales);
    }

    @Operation(
    	    summary = "Get sale by ID",
    	    description = "Retrieves the details of a specific sale by its ID"
    	)
    @GetMapping("/{saleId}")
    @PreAuthorize("hasRole('SALES_EMPLOYEE')")
    public ResponseEntity<SaleResponseDTO> getSaleById(@PathVariable Long saleId) {
        SaleResponseDTO sale = saleService.getSaleById(saleId);
        return ResponseEntity.ok(sale);
    }

    @Operation(
    	    summary = "Get sales by user ID",
    	    description = "Retrieves all sales created by a specific user (admin only)"
    	)
    @GetMapping("/user/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<SaleResponseDTO>> getSalesByUser(@PathVariable Long userId) {
        List<SaleResponseDTO> sales = saleService.getSalesByUser(userId);
        return ResponseEntity.ok(sales);
    }

    @Operation(
    	    summary = "Get sales by customer ID",
    	    description = "Retrieves all sales made to a specific customer"
    	)
    @GetMapping("/customer/{customerId}")
    @PreAuthorize("hasRole('SALES_EMPLOYEE')")
    public ResponseEntity<List<SaleResponseDTO>> getSalesByCustomer(@PathVariable Long customerId) {
        List<SaleResponseDTO> sales = saleService.getSalesByCustomer(customerId);
        return ResponseEntity.ok(sales);
    }

    @Operation(
    	    summary = "Generate customer sales report",
    	    description = "Generates a summary report for a specific customer's sales (admin only)"
    	)
    @GetMapping("/customer/{customerId}/report")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CustomerSalesReportDTO> generateCustomerSalesReport(@PathVariable Long customerId) {
        CustomerSalesReportDTO report = saleService.generateCustomerSalesReport(customerId);
        return ResponseEntity.ok(report);
    }
    
    @Operation(
    	    summary = "Get total sales for a customer",
    	    description = "Retrieves the total sales amount for a specific customer"
    	)
    @GetMapping("/customer/{customerId}/total-sales")
    @PreAuthorize("hasRole('ADMIN') or hasRole('SALES_EMPLOYEE')")
    public ResponseEntity<BigDecimal> getCustomerTotalSales(@PathVariable Long customerId) {
        BigDecimal totalSales = saleService.getCustomerTotalSales(customerId);
        return ResponseEntity.ok(totalSales);
    }
     
    @Operation(
    	    summary = "Download sale invoice as PDF",
    	    description = "Generates and downloads the invoice PDF for a specific sale"
    	)
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