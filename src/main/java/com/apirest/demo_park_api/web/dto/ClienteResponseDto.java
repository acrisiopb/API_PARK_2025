package com.apirest.demo_park_api.web.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ClienteResponseDto {
    private Long id;
    private String nome;
    private String cpf;
}
