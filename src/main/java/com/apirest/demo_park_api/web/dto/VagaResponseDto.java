package com.apirest.demo_park_api.web.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor @NoArgsConstructor
public class VagaResponseDto {
    
    private Long id;
    private String codigo;
    private String status;
}
