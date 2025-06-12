package com.apirest.demo_park_api.web.dto;

import org.hibernate.validator.constraints.br.CPF;
import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EstacionamentoCreateDTO {

    @NotBlank
    @Size(min = 8, max = 8)
    @Pattern(regexp = "[A-Z]{3}-[0-9]{4}", message = "A placa do veiculo deve seguir o padrão 'XXX-0000'")
    private String placa;

    @NotBlank
    private String marca;

    @NotBlank
    private String modelo;

    @NotBlank
    private String cor;

    @NotBlank
    @Size(min = 11, max = 11)
    @CPF
    private String clienteCpf;

}
