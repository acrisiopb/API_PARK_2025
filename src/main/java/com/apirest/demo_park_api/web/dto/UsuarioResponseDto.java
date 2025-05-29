package com.apirest.demo_park_api.web.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UsuarioResponseDto {

    private Long id;
    private String username;
    private String role;

}
