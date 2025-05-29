package com.apirest.demo_park_api.web.dto;

import java.io.Serializable;

import com.apirest.demo_park_api.entity.Usuario;
import com.apirest.demo_park_api.entity.Usuario.Role;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class UsuarioCreateDTO {

    private String username;
    private String password;

    public UsuarioCreateDTO(Usuario user) {
        this.username = user.getUsername();
        this.password = user.getPassword();

    }

}
