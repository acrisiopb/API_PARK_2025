package com.apirest.demo_park_api.web.dto.mapper;

import java.util.List;
import java.util.stream.Collectors;

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

    /**
     * Converte uma entidade Usuario para um DTO de resposta (UsuarioResponseDto).
     * Remove o prefixo "ROLE_" do nome do papel (role) do usuário antes de
     * atribuí-lo ao DTO.
     */
    public static UsuarioResponseDto toDTO(Usuario usuario) {
        // Extrai o nome da role, removendo o prefixo "ROLE_"
        String role = usuario.getRole().name().substring("ROLE_".length());

        // Define um mapeamento personalizado entre Usuario e UsuarioResponseDto
        PropertyMap<Usuario, UsuarioResponseDto> props = new PropertyMap<Usuario, UsuarioResponseDto>() {
            @Override
            protected void configure() {
                // Define como o valor da role será atribuído no DTO
                map().setRole(role);
            }
        };

        // Cria um ModelMapper e aplica o mapeamento personalizado
        ModelMapper mapper = new ModelMapper();
        mapper.addMappings(props);

        // Converte o objeto Usuario para UsuarioResponseDto e retorna
        return mapper.map(usuario, UsuarioResponseDto.class);
    }

    /**
     * Converte uma lista de entidades Usuario em uma lista de UsuarioResponseDto.
     * Utiliza o método toDTO para cada item da lista.
     */
    public static List<UsuarioResponseDto> toListDto(List<Usuario> usuarios) {
        return usuarios.stream()
                .map(user -> toDTO(user)) // Converte cada Usuario para DTO
                .collect(Collectors.toList()); // Coleta os resultados em uma nova lista
    }
}
