package com.jewelry.managementsystem.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        ///  Bearer JWT
        SecurityScheme bearerScheme = new SecurityScheme()
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT")
                .description("JWT Bearer token");

        SecurityRequirement bearerRequirement = new SecurityRequirement()
                .addList("Bearer Authentication");



        return new OpenAPI()
                .info(new Info()
                        .title("Jewelry Management System API")
                        .version("1.0")
                        .description("Professional E-commerce API for fine jewelry inventory, shopping cart, and order processing.")
                        .contact(new Contact()
                                .name("Johan Yahir Villalpando Ibarra")
                                .email("johanvibarra7@gmail.com")
                                .url("https://github.com/HanjoBulKing7"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("http://springdoc.org")))
                .components(
                        new Components()
                                .addSecuritySchemes("Bearer Authenticatino", bearerScheme))
                .addSecurityItem(bearerRequirement);
    }
}