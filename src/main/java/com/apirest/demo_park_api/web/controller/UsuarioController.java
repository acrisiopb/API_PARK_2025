package com.apirest.demo_park_api.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.apirest.demo_park_api.entity.Usuario;
import com.apirest.demo_park_api.service.UsuarioService;
import com.apirest.demo_park_api.web.dto.UsuarioCreateDTO;
import com.apirest.demo_park_api.web.dto.UsuarioResponseDto;
import com.apirest.demo_park_api.web.dto.UsuarioSenhaDto;
import com.apirest.demo_park_api.web.dto.mapper.UsuarioMapper;
import com.apirest.demo_park_api.web.exception.ErrorMessage;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Tag(name = "Usuarios", description = "Contém todas as operações relativas aos recursos para cadastro, edição e leitura de um usuário.")
@RequiredArgsConstructor // injeção de dependencia via construtor / lombok
@RestController
@RequestMapping("api/v1/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    // CRIAR USUARIO
    @Operation(summary = "Criar um novo usuário.", description = "Recurso para criar um novo usuário.", responses = {

            @ApiResponse(responseCode = "201", description = "Recurso criado com sucesso.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UsuarioResponseDto.class))),

            @ApiResponse(responseCode = "409", description = "Usuário e-mail já cadastrado no sistema.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),

            @ApiResponse(responseCode = "422", description = "Recursos não processado por dados de entrada invalidos.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
    })
    @PostMapping
    public ResponseEntity<UsuarioResponseDto> create(@Valid @RequestBody UsuarioCreateDTO createDTO) {
        Usuario user = usuarioService.salvar(UsuarioMapper.toUsuario(createDTO));
        return ResponseEntity.status(HttpStatus.CREATED).body(UsuarioMapper.toDTO(user));
    }

    // BUSCAR USUARIO POR ID
    @Operation(summary = "Recuperar um usuário pelo id.", description = "Recuperar um usuário pelo id.", responses = {

            @ApiResponse(responseCode = "200", description = "Recurso recuperado com sucesso.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UsuarioResponseDto.class))),

            @ApiResponse(responseCode = "404", description = "Recurso não encontrado.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
    })
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UsuarioResponseDto> getById(@PathVariable Long id) {
        Usuario user = usuarioService.buscarPorId(id);
        return ResponseEntity.ok(UsuarioMapper.toDTO(user));
    }

    // ALTERAR SENHA
    @Operation(summary = "Atualizar senha.", description = "Atualizar senha.", responses = {

            @ApiResponse(responseCode = "204", description = "Senha atualizada com sucesso.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Void.class))),

            @ApiResponse(responseCode = "400", description = "Senha não confere.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),

            @ApiResponse(responseCode = "404", description = "Recurso não encontrado.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),

            @ApiResponse(responseCode = "422", description = "Erro de validação nos campos enviados. Verifique os detalhes e tente novamente.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
    })
    @PatchMapping("/{id}")
    public ResponseEntity<Void> upadatePassword(@PathVariable Long id, @Valid @RequestBody UsuarioSenhaDto dto) {
        Usuario user = usuarioService.editarSenha(id, dto.getSenhaAtual(), dto.getNovaSenha(), dto.getConfirmaSenha());
        // Retorna uma resposta HTTP com o status 204 No Content.
        // Esse status indica que a requisição foi processada com sucesso,
        // mas que não há conteúdo para ser retornado no corpo da resposta.
        // Muito usado, por exemplo, após uma exclusão (DELETE) bem-sucedida.
        return ResponseEntity.noContent().build();
    }

    // BUSCAR TODOS OS USUARIOS
    @Operation(summary = "Lista todos os usuários.", description = "Lista todos os usuários.", responses = {
            @ApiResponse(responseCode = "200", description = "Usuários recuperado com sucesso.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UsuarioResponseDto.class))),

    })
    @GetMapping
    public ResponseEntity<List<UsuarioResponseDto>> getAll() {
        List<Usuario> users = usuarioService.buscarTodos();
        return ResponseEntity.ok(UsuarioMapper.toListDto(users));
    }

}
