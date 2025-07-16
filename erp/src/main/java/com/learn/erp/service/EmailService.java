package com.learn.erp.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.learn.erp.model.Customer;
import com.learn.erp.model.LeaveRequest;
import com.learn.erp.model.Product;
import com.learn.erp.model.Sale;
import com.learn.erp.model.User;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender javaMailSender;
    private final TemplateEngine templateEngine;

    @Value("${spring.mail.username}")
    private String fromEmail;
   
   
    public void sendWelcomeEmail(User user) {
        sendEmail(
            user.getEmail(),
            "Welcome to ERP System!",
            "emails/welcome", 
            new ContextBuilder()
                .add("username", user.getUsername()) 
                .build()
        );
    }
    
    public void sendEmployeeWelcomeEmail(User user) {
        sendEmail(
            user.getEmail(),
            "You're officially part of the team!",
            "emails/employee-welcome", 
            new ContextBuilder()
                .add("username", user.getUsername()) 
                .build()
        );
    }

    public void sendAbsenceAlert(User user) {
        sendEmail(
            user.getEmail(),
            "Absence Alert - Missed Check-in",
            "emails/absent-alert",
            new ContextBuilder()
                .add("username", user.getUsername())
                .build()
        );
    }
    
    public void sendLeaveApproved(User user, LeaveRequest request) {
        sendEmail(
            user.getEmail(),
            "Leave Request Approved",
            "emails/leave-approved",
            new ContextBuilder()
                .add("username", user.getUsername())
                .add("startDate", request.getStartDate())
                .add("endDate", request.getEndDate())
                .build()
        );
    }
    
    public void sendLeaveRejected(User user, LeaveRequest request) {
        sendEmail(
            user.getEmail(),
            "Leave Request Rejected",
            "emails/leave-rejected",
            new ContextBuilder()
                .add("username", user.getUsername())
                .add("startDate", request.getStartDate())
                .add("endDate", request.getEndDate())
                .build()
        );
    }
    
    public void sendLowStockAlert(User manager, Product product) {
        sendEmail(
            manager.getEmail(),
            "Low Stock Alert: " + product.getName(),
            "emails/low-stock-alert",
            new ContextBuilder()
                .add("username", manager.getFullName())
                .add("productName", product.getName())
                .add("stock", product.getStock())
                .build()
        );
    }

    public void sendSaleConfirmation(Customer customer, Sale sale) {
        sendEmail(
            customer.getEmail(),
            "Sale Confirmation - Order #" + sale.getSaleId(),
            "emails/sale-confirmation",
            new ContextBuilder()
                .add("customerName", customer.getName())
                .add("saleId", sale.getSaleId())
                .add("totalAmount", sale.getTotalAmount())
                .build()
        );
    }
    
    public void sendSaleInvoiceWithPdf(String toEmail, Long saleId, BigDecimal totalAmount, byte[] pdfBytes) {
        sendEmailWithAttachment(
            toEmail,
            "Sale Invoice #" + saleId,
            "emails/sale-confirmation",
            new ContextBuilder()
                .add("saleId", saleId)
                .add("totalAmount", totalAmount)
                .build(),
            pdfBytes,
            "Invoice-" + saleId + ".pdf"
        );
    }

    
    
    public void sendCode(User user, String subject) {
        sendEmail(
                user.getEmail(),
                subject,
                "emails/send-code", // template path inside /resources/templates
                new ContextBuilder()
                        .add("name", user.getFullName())
                        .add("code", user.getRequestCode())
                        .build()
        );
    }

    

    private void sendEmail(String to, String subject, String templatePath, Context context) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setFrom(fromEmail);
            helper.setTo(to);
            helper.setSubject(subject);

            String htmlContent = templateEngine.process(templatePath, context);
            helper.setText(htmlContent, true);

            javaMailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace(); // اطبع الخطأ
            throw new RuntimeException("Failed to send email", e);
        }
    }

    
    private void sendEmailWithAttachment(
            String to,
            String subject,
            String templatePath,
            Context context,
            byte[] attachmentBytes,
            String attachmentFilename
    ) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setFrom(fromEmail);
            helper.setTo(to);
            helper.setSubject(subject);

            String htmlContent = templateEngine.process(templatePath, context);
            helper.setText(htmlContent, true);

            // Attach the PDF
            ByteArrayResource pdfAttachment = new ByteArrayResource(attachmentBytes);
            helper.addAttachment(attachmentFilename, pdfAttachment);

            javaMailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to send email with attachment", e);
        }
    }

    // Nested helper class to make adding context variables easier
    private static class ContextBuilder {
        private final Context context = new Context();

        public ContextBuilder add(String key, Object value) {
            context.setVariable(key, value);
            return this;
        }

        public Context build() {
            return context;
        }
    }
}