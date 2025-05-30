package com.apirest.demo_park_api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@Configuration
public class SpringDocOpenApiConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(
                        new Info()
                                .title("REST API - Spring Park")
                                .description("API para gestão de estabelecimento de veiculos")
                                .version("v1")
                                .license(new License().name("Github | API - Park")
                                        .url("https://github.com/acrisiopb/API_PARK_2025"))
                                .contact(new Contact().name("Acrísio Dev").email("Acrisiopb@gmail.com")));
    }
}
