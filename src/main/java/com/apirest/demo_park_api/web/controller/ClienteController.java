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
import org.springframework.web.bind.annotation.RequestBody;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/clientes")
public class ClienteController {

    private final ClienteService clienteService;
    private final UsuarioService usuarioService;

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
