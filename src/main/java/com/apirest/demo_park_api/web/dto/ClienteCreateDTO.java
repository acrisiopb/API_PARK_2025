package com.apirest.demo_park_api.web.dto;

import org.hibernate.validator.constraints.br.CPF;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ClienteCreateDTO {
    @NotNull   
    @Size(min = 5, max = 100)
    private String nome;
    
    @Size(min = 11, max = 11)
    @CPF
    private String cpf;
}
