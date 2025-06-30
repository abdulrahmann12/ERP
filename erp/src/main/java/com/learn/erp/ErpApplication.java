package com.learn.erp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = "com.learn.erp.model")
public class ErpApplication {

	public static void main(String[] args) {
		SpringApplication.run(ErpApplication.class, args);
	}
}
