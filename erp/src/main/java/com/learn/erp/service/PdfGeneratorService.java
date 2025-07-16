package com.learn.erp.service;

import com.learn.erp.dto.SaleResponseDTO;
import com.learn.erp.exception.SaleNotFoundException;
import com.learn.erp.mapper.SaleMapper;
import com.learn.erp.model.Sale;
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
    private final SaleMapper saleMapper;
    private final TemplateEngine templateEngine;

    @Autowired
    public PdfGeneratorService(SaleRepository saleRepository, SaleMapper saleMapper) {
        this.saleRepository = saleRepository;
        this.saleMapper = saleMapper;

        ClassLoaderTemplateResolver resolver = new ClassLoaderTemplateResolver();
        resolver.setPrefix("/templates/");
        resolver.setSuffix(".html");
        resolver.setTemplateMode("HTML");
        resolver.setCharacterEncoding("UTF-8");

        this.templateEngine = new TemplateEngine();
        this.templateEngine.setTemplateResolver(resolver);
    }

    public byte[] generateSalePdf(SaleResponseDTO dto) {
        Context context = new Context();
        String formattedDate = dto.getDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        context.setVariable("formattedDate", formattedDate);
        context.setVariable("sale", dto);
        context.setVariable("customer", dto.getCustomer());
        context.setVariable("user", dto.getUser());
        context.setVariable("items", dto.getItems());

        String html = templateEngine.process("pdf/sale-invoice", context);

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

    public byte[] generateSalePdfById(Long saleId) {
        Sale sale = saleRepository.findById(saleId)
                .orElseThrow(SaleNotFoundException::new);
        SaleResponseDTO dto = saleMapper.toDTO(sale);
        return generateSalePdf(dto); 
    }
}