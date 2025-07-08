package com.learn.erp.service;


import com.learn.erp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;


@Service
public class PdfGeneratorService {

    private final TemplateEngine templateEngine;
    private final UserRepository userRepository;

    @Autowired
    public PdfGeneratorService(UserRepository userRepository) {
        this.userRepository = userRepository;

        ClassLoaderTemplateResolver resolver = new ClassLoaderTemplateResolver();
        resolver.setPrefix("/templates/");
        resolver.setSuffix(".html");
        resolver.setTemplateMode("XHTML");
        resolver.setCharacterEncoding("UTF-8");

        this.templateEngine = new TemplateEngine();
        this.templateEngine.setTemplateResolver(resolver);
    }

}
