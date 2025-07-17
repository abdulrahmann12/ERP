package com.learn.erp.service;

import com.learn.erp.dto.PurchaseResponseDTO;
import com.learn.erp.dto.SaleResponseDTO;
import com.learn.erp.exception.PurchaseNotFoundException;
import com.learn.erp.exception.SaleNotFoundException;
import com.learn.erp.mapper.PurchaseMapper;
import com.learn.erp.mapper.SaleMapper;
import com.learn.erp.model.Purchase;
import com.learn.erp.model.Sale;
import com.learn.erp.repository.PurchaseRepository;
import com.learn.erp.repository.SaleRepository;
import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import java.io.ByteArrayOutputStream;
import java.time.format.DateTimeFormatter;

@Service
public class PdfGeneratorService {

    private final SaleRepository saleRepository;
    private final PurchaseRepository purchaseRepository;
    private final SaleMapper saleMapper;
    private final PurchaseMapper purchaseMapper;
    private final TemplateEngine templateEngine;

    @Autowired
    public PdfGeneratorService(
        SaleRepository saleRepository,
        PurchaseRepository purchaseRepository,
        SaleMapper saleMapper,
        PurchaseMapper purchaseMapper
    ) {
        this.saleRepository = saleRepository;
        this.purchaseRepository = purchaseRepository;
        this.saleMapper = saleMapper;
        this.purchaseMapper = purchaseMapper;

        ClassLoaderTemplateResolver resolver = new ClassLoaderTemplateResolver();
        resolver.setPrefix("/templates/");
        resolver.setSuffix(".html");
        resolver.setTemplateMode("HTML");
        resolver.setCharacterEncoding("UTF-8");

        this.templateEngine = new TemplateEngine();
        this.templateEngine.setTemplateResolver(resolver);
    }

    // ---------- Sale ----------
    public byte[] generateSalePdf(SaleResponseDTO dto) {
        Context context = new Context();
        String formattedDate = dto.getDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        context.setVariable("formattedDate", formattedDate);
        context.setVariable("sale", dto);
        context.setVariable("customer", dto.getCustomer());
        context.setVariable("user", dto.getUser());
        context.setVariable("items", dto.getItems());

        String html = templateEngine.process("pdf/sale-invoice", context);

        return renderPdf(html);
    }

    public byte[] generateSalePdfById(Long saleId) {
        Sale sale = saleRepository.findById(saleId)
                .orElseThrow(SaleNotFoundException::new);
        SaleResponseDTO dto = saleMapper.toDTO(sale);
        return generateSalePdf(dto);
    }

    // ---------- Purchase ----------
    public byte[] generatePurchasePdf(PurchaseResponseDTO dto) {
        Context context = new Context();
        String formattedDate = dto.getDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        context.setVariable("formattedDate", formattedDate);
        context.setVariable("purchase", dto);
        context.setVariable("supplier", dto.getSupplier());
        context.setVariable("user", dto.getUser());
        context.setVariable("items", dto.getItems());

        String html = templateEngine.process("pdf/purchase-invoice", context);

        return renderPdf(html);
    }

    public byte[] generatePurchasePdfById(Long purchaseId) {
        Purchase purchase = purchaseRepository.findById(purchaseId)
                .orElseThrow(PurchaseNotFoundException::new);
        PurchaseResponseDTO dto = purchaseMapper.toDTO(purchase);
        return generatePurchasePdf(dto);
    }

    // ---------- Helper ----------
    private byte[] renderPdf(String html) {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            PdfRendererBuilder builder = new PdfRendererBuilder();
            builder.useFastMode();
            builder.withHtmlContent(html, null);
            builder.toStream(outputStream);
            builder.run();
            return outputStream.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate PDF", e);
        }
    }
}
