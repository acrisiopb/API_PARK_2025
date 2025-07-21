package com.apirest.demo_park_api.web.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.apirest.demo_park_api.jwt.JwtToken;
import com.apirest.demo_park_api.jwt.JwtUserDetailsService;
import com.apirest.demo_park_api.web.dto.UsuarioLoginDto;
import com.apirest.demo_park_api.web.dto.UsuarioResponseDto;
import com.apirest.demo_park_api.web.exception.ErrorMessage;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.web.bind.annotation.RequestBody;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Tag(name = "Autenticação", description = "Recurso para autenticação de API")
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class AutenticacaoController {

    private final JwtUserDetailsService detailsService;
    private final AuthenticationManager authenticationManager;

    @Operation(summary = "Autenticar na API.", description = "Recurso de autenticação na API.", responses = {

            @ApiResponse(responseCode = "200", description = "Autenticação realizada com sucesso e retorno de um bearer Token.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UsuarioResponseDto.class))),

            @ApiResponse(responseCode = "409", description = "Credenciais inválidas.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),

            @ApiResponse(responseCode = "422", description = "Campo(s) Inválido(s).", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
    })
    @PostMapping("/auth")
    public ResponseEntity<?> authenticar(@RequestBody @Valid UsuarioLoginDto dto, HttpServletRequest request) {
        log.info("Processo de autenticação pelo login {}", dto.getUsername());

        try {
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    dto.getUsername(), dto.getPassword());
            authenticationManager.authenticate(authenticationToken);
            JwtToken token = detailsService.getTokenAuthenticated(dto.getUsername());
            return ResponseEntity.ok(token);

        } catch (AuthenticationException ex) {
            log.warn("Bad credentials from username '{}'", dto.getUsername());
        }
        return ResponseEntity.unprocessableEntity()
                .body(new ErrorMessage(request, HttpStatus.UNPROCESSABLE_ENTITY, "Credenciais Inválidas"));
    }


   @Operation(summary = "Manter a API sempre Ativa - Render.", description = "", security = @SecurityRequirement(name = "security"), responses = {

            @ApiResponse(responseCode = "200", description = "", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Void.class))),

    })
    @GetMapping("/ping")
    public ResponseEntity<String> ping() {
        return ResponseEntity.ok("pong");
    }

}
