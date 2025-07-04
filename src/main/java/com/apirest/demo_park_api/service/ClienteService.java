package com.apirest.demo_park_api.service;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.apirest.demo_park_api.entity.Cliente;
import com.apirest.demo_park_api.exception.EntityNotFoundException;
import com.apirest.demo_park_api.repository.ClienteRepository;
import com.apirest.demo_park_api.repository.projection.ClienteProjection;
import com.apirest.demo_park_api.web.exception.CpfUniqueViolationException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ClienteService {
    private final ClienteRepository clienteRepository;

    @Transactional
    public Cliente salvar(Cliente cliente) {
        try {
            return clienteRepository.save(cliente);
        } catch (DataIntegrityViolationException ex) {
            throw new CpfUniqueViolationException(
                    String.format("CPF '%s' não pode ser cadastrado, já existe no sistema", cliente.getCpf()));
        }
    }

    @Transactional(readOnly = true)
    public Cliente buscarPorId(Long id) {
        return clienteRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(String.format("Cliente  id = %s , não encontrado.", id)));
    }

    @Transactional(readOnly = true)
    public Page<ClienteProjection> buscarTodos(Pageable pageable) {
        return clienteRepository.findAllPageable(pageable);

    }

    @Transactional(readOnly = true)
    public Cliente buscarPorUsuarioId(Long id) {
        return clienteRepository.findByUsuarioId(id);
    }

    public Cliente buscarPorCpf(String cpf) {
        
        return clienteRepository.findByCpf(cpf).orElseThrow(
            () -> new EntityNotFoundException(String.format("Cliente com CPF %s não encontrado ",cpf))
        );

    }

}