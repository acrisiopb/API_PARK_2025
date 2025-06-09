package com.apirest.demo_park_api.web.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.apirest.demo_park_api.entity.Cliente;
import com.apirest.demo_park_api.jwt.JwtUserDetails;
import com.apirest.demo_park_api.service.ClienteService;
import com.apirest.demo_park_api.service.UsuarioService;
import com.apirest.demo_park_api.web.dto.ClienteCreateDTO;
import com.apirest.demo_park_api.web.dto.ClienteResponseDto;
import com.apirest.demo_park_api.web.dto.mapper.ClienteMapper;
import com.apirest.demo_park_api.web.exception.ErrorMessage;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.web.bind.annotation.RequestBody;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
@Tag(name = "Clientes", description = "Contém todas as operações relativas ao recurso de um cliente")
@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/clientes")
public class ClienteController {

    private final ClienteService clienteService;
    private final UsuarioService usuarioService;

    @Operation(summary = "Criar um novo cliente.", description = "Recurso para criar um novo cliente vinculado a um usuario Cadastrado, "
            +
            "Requisição exige uso de um bearer token. Acesso restrito a Role = 'Cliente' ", responses = {

                    @ApiResponse(responseCode = "201", description = "Recurso criado com sucesso.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ClienteResponseDto.class))),

                    @ApiResponse(responseCode = "409", description = "Cliente CPF já possui cadastro no sistema.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),

                    @ApiResponse(responseCode = "422", description = "Recursos não processado por dados de entrada invalidos.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),

                    @ApiResponse(responseCode = "403", description = "Recursos não permitido ao perfil ADMIN.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))

    })
    @PostMapping
    @PreAuthorize("hasRole('CLIENTE')")
    public ResponseEntity<ClienteResponseDto> create(@RequestBody @Valid ClienteCreateDTO dto,
            @AuthenticationPrincipal JwtUserDetails userDetails) {
        Cliente cliente = ClienteMapper.toCliente(dto);
        cliente.setUsuaurio(usuarioService.buscarPorId(userDetails.getId()));
        clienteService.salvar(cliente);
        return ResponseEntity.status(201).body(ClienteMapper.toDto(cliente));
    }

}
