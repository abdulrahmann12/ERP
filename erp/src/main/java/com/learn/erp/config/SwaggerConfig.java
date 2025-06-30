package com.learn.erp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
            .info(new Info()
                .title("ERP API")
                .version("1.0")
                .description("Backend API for managing a comprehensive ERP system with modules for user authentication, employee records, departments, attendance, leave requests, payroll, inventory, sales, purchases, and reporting.")
                .contact(new Contact()
                    .name("abdulraman Ahmed")
                    .email("abdulraman.ahmedd@gmail.com")
                )
            );
    }
}