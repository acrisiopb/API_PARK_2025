package com.apirest.demo_park_api.web.dto;

import java.io.Serializable;

import com.apirest.demo_park_api.entity.Usuario;
import com.apirest.demo_park_api.entity.Usuario.Role;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class UsuarioCreateDTO {
    @NotBlank
    @Email(regexp = "^[a-z0-9.+-]+@[a-z0-9.-]+\\.[a-z]{2,}$", message = "Formato do e-mail esta invalido")
    private String username;

    @NotBlank
    @Size(min = 6, max = 6)
    private String password;

}
