package com.apirest.demo_park_api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
public class SpringDocOpenApiConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
        .components(new Components().addSecuritySchemes("security", securityScheme() ))
                .info(
                        new Info()
                                .title("REST API - Spring Park")
                                .description("API para gestão de estabelecimento de veiculos")
                                .version("v1")
                                .license(new License().name("Github | API - Park")
                                        .url("https://github.com/acrisiopb/API_PARK_2025"))
                                .contact(new Contact().name("Acrísio Dev").email("Acrisiopb@gmail.com")));
    }

    private SecurityScheme securityScheme(){
        return new SecurityScheme()
        .description("Insira um bearer token valido para prosseguir")
        .type(SecurityScheme.Type.HTTP)
        .in(SecurityScheme.In.HEADER)
        .scheme("bearer")
        .bearerFormat("JWT")
        .name("security");
    } 
}
