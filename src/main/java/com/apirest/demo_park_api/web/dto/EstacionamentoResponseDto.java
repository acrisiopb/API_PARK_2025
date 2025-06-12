package com.apirest.demo_park_api.web.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EstacionamentoResponseDto {

    private String placa;

    private String marca;

    private String modelo;

    private String cor;

    private String clienteCpf;

    private String recibo;

    private LocalDateTime dateTime;

    private LocalDateTime dataSaida;

    private String vagaCodigo;

    private BigDecimal valor;

    private BigDecimal desconto;

}
