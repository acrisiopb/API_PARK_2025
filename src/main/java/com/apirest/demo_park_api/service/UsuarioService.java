package com.apirest.demo_park_api.service;

import org.springframework.stereotype.Service;

import com.apirest.demo_park_api.entity.Usuario;
import com.apirest.demo_park_api.repository.UsuarioRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor // injeção de dependencia via construtor / lombok
@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    @Transactional
    public Usuario salvar(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

}
