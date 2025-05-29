package com.apirest.demo_park_api.web.dto.mapper;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;

import com.apirest.demo_park_api.entity.Usuario;
import com.apirest.demo_park_api.web.dto.UsuarioCreateDTO;
import com.apirest.demo_park_api.web.dto.UsuarioResponseDto;

/**
 * Classe responsável por realizar a conversão (mapeamento) de um objeto DTO
 * (Data Transfer Object)
 * para uma entidade do tipo Usuario.
 * 
 * Utiliza a biblioteca ModelMapper para facilitar a cópia de dados entre
 * objetos,
 * evitando a necessidade de atribuir manualmente cada campo.
 * 
 * O método estático toUsuario recebe um objeto do tipo UsuarioCreateDTO, que
 * geralmente contém
 * os dados fornecidos pelo cliente (por exemplo, através de uma requisição
 * HTTP), e retorna
 * um novo objeto Usuario com os mesmos dados.
 * 
 * Essa prática é comum em aplicações REST para separar as camadas de entrada
 * (DTOs)
 * da lógica de negócios (Entidades).
 */
public class UsuarioMapper {
    // Converte um objeto UsuarioCreateDTO para um objeto Usuario
    public static Usuario toUsuario(UsuarioCreateDTO createDTO) {
        return new ModelMapper().map(createDTO, Usuario.class);
    }

    public static UsuarioResponseDto toDTO(Usuario usuario) {
        String role = usuario.getRole().name().substring("ROLE_".length());
        PropertyMap<Usuario, UsuarioResponseDto> props = new PropertyMap<Usuario, UsuarioResponseDto>() {
            @Override
            protected void configure() {
                map().setRole(role);
            }
        };
        ModelMapper mapper = new ModelMapper();
        mapper.addMappings(props);
        return mapper.map(usuario, UsuarioResponseDto.class);
    }
}
