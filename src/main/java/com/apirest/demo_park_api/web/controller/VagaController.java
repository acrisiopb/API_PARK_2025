package com.apirest.demo_park_api.web.controller;

import java.net.URI;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.apirest.demo_park_api.entity.Vaga;
import com.apirest.demo_park_api.service.VagaService;
import com.apirest.demo_park_api.web.dto.VagaCreateDTO;
import com.apirest.demo_park_api.web.dto.VagaResponseDto;
import com.apirest.demo_park_api.web.dto.mapper.VagaMapper;
import com.apirest.demo_park_api.web.exception.ErrorMessage;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/vagas")
public class VagaController {

    private final VagaService vagaService;

    @Operation(summary = "Criar uma nova vaga", description = "Recurso para criar uma nova vaga."
            + "Requisição exige uso de um bearer token. Acesso restrito e Role='ADMIN'", responses = {
                    @ApiResponse(responseCode = "201", description = "Recurso criado com sucesso.", headers = @Header(name = HttpHeaders.LOCATION, description = "URL do recurso criado.")),
                    @ApiResponse(responseCode = "409", description = "Vaga já cadastrada", content = @Content(mediaType = "application/json;charset=UTF-8", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "422", description = "Recurso não processado por falta de dados ou dados inválidos", content = @Content(mediaType = "application/json;charset=utf8", schema = @Schema(implementation = ErrorMessage.class)))

    })
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> create(@RequestBody @Valid VagaCreateDTO dto) {
        Vaga vaga = VagaMapper.toVaga(dto);
        vagaService.salvar(vaga);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequestUri().path("/{codigo}")
                .buildAndExpand(vaga.getCodigo())
                .toUri();
        return ResponseEntity.created(location).build();
    }


    
      @Operation(summary = "Localizar uma vaga", description = "Recurso para retorna uma vaga pelo código."
            + "Requisição exige uso de um bearer token. Acesso restrito e Role='ADMIN'", 
            responses = {
                    @ApiResponse(responseCode = "201", description = "Recurso criado com sucesso.", headers = @Header(name = HttpHeaders.LOCATION, description = "URL do recurso criado.")),
                    @ApiResponse(responseCode = "409", description = "Vaga já cadastrada", content = @Content(mediaType = "application/json;charset=UTF-8", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "422", description = "Recurso não processado por falta de dados ou dados inválidos", content = @Content(mediaType = "application/json;charset=utf8", schema = @Schema(implementation = ErrorMessage.class)))

    })
    @GetMapping("/{codigo}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<VagaResponseDto> getByCodigo(@PathVariable String codigo) {
        Vaga vaga = vagaService.BuscarPorCodigo(codigo);
        return ResponseEntity.ok(VagaMapper.toDto(vaga));
    }
}