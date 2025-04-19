package com.beratoztas.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
public class SwaggerConfig {

	@Bean
	public OpenAPI customOpenAPI() {
		return new OpenAPI()
				 .addSecurityItem(new SecurityRequirement().addList("BearerAuth"))
				 .components(new Components().addSecuritySchemes("BearerAuth",
			                new SecurityScheme()
		                    .name("Authorization")
		                    .type(SecurityScheme.Type.HTTP)
		                    .scheme("bearer")
		                    .bearerFormat("JWT")))
				.info(new Info()
				.title("BeratOztas E-Commerce API")
				.version("1.0")
				.description("Portfolio project for showcasing Spring Boot + JWT + Redis + RabbitMQ")
				.contact(new Contact().name("Berat Oztas").email("berat.oztas.dev@gmail.com"))
				);
	}
}
