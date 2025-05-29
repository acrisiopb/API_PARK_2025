package com.apirest.demo_park_api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apirest.demo_park_api.repository.UsuarioRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor // injeção de dependencia via construtor / lombok
@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

}
