package com.apirest.demo_park_api.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class VagaCreateDTO {
    
    @NotBlank(message = "{NotBlank.vagaCreateDTO.codigo}")
    @Size(min = 4, max = 4, message = "{NotBlank.vagaCreateDTO.codigo}")
    private String codigo;

    @NotBlank(message = "{NotBlank.vagaCreateDTO.status}")
    @Pattern(regexp = "LIVRE|OCUPADA", message = "{Pattern.vagaCreateDTO.status}")
    private String status;
}
