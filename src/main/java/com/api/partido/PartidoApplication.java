package com.api.partido;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@OpenAPIDefinition
@ComponentScan(basePackages = {"com.f5app.eventpublisher", "com.api.partido"})
public class PartidoApplication {
	public static void main(String[] args) {
		SpringApplication.run(PartidoApplication.class, args);
	}
}