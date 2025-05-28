package com.apirest.demo_park_api.config;

import java.util.TimeZone;

import org.springframework.context.annotation.Configuration;

import jakarta.annotation.PostConstruct;

//Configuração do fuso horário do projeto de acordo com o país
@Configuration
public class SpringTimeZoneConfig {

    @PostConstruct
    public void timezoneConfig() {
        TimeZone.setDefault(TimeZone.getTimeZone("America/Sao_paulo"));
    }
}
