package com.apirest.demo_park_api.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor // injeção de dependencia via construtor / lombok
@RestController
@RequestMapping("api/v1/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    // CRIAR USUARIO
    @PostMapping
    public ResponseEntity<UsuarioResponseDto> create(@RequestBody UsuarioCreateDTO createDTO) {
        Usuario user = usuarioService.salvar(UsuarioMapper.toUsuario(createDTO));
        return ResponseEntity.status(HttpStatus.CREATED).body(UsuarioMapper.toDTO(user));
    }

    // BUSCAR USUARIO POR ID
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponseDto> getById(@PathVariable Long id) {
        Usuario user = usuarioService.buscarPorId(id);
        return ResponseEntity.ok(UsuarioMapper.toDTO(user));
    }

    // ALTERAR SENHA
    @PutMapping("/{id}")
    public ResponseEntity<Void> upadatePassword(@PathVariable Long id, @RequestBody UsuarioSenhaDto dto) {
        Usuario user = usuarioService.editarSenha(id, dto.getSenhaAtual(), dto.getNovaSenha(), dto.getConfirmaSenha());
        // Retorna uma resposta HTTP com o status 204 No Content.
        // Esse status indica que a requisição foi processada com sucesso,
        // mas que não há conteúdo para ser retornado no corpo da resposta.
        // Muito usado, por exemplo, após uma exclusão (DELETE) bem-sucedida.
        return ResponseEntity.noContent().build();
    }

    // BUSCAR TODOS OS USUARIOS
    @GetMapping
    public ResponseEntity<List<UsuarioResponseDto>> getAll() {
        List<Usuario> users = usuarioService.buscarTodos();
        return ResponseEntity.ok(UsuarioMapper.toListDto(users));
    }

}
