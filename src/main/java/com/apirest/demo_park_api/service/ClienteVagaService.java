package com.apirest.demo_park_api.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apirest.demo_park_api.entity.ClienteVaga;
import com.apirest.demo_park_api.exception.EntityNotFoundException;
import com.apirest.demo_park_api.repository.ClienteVagaRepository;
import com.apirest.demo_park_api.repository.projection.ClienteVagaProjection;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ClienteVagaService {

  private final ClienteVagaRepository repository;

  @Transactional
  public ClienteVaga salvar(ClienteVaga clienteVaga) {
    return repository.save(clienteVaga);
  }

  @Transactional(readOnly = true)
  public ClienteVaga buscarPorRecibo(String recibo) {
    return repository.findByReciboAndDataSaidaIsNull(recibo).orElseThrow(
        () -> new EntityNotFoundException(

            String.format("Recibo %s não encontrado no sistema ou check-out já realizado.", recibo)));
  }

  @Transactional(readOnly = true)
  public long getTotalDeVezesEstacionamentoCompleto(String cpf) {
    return repository.countByClienteCpfAndDataSaidaIsNotNull(cpf);
  }

  @Transactional(readOnly = true)
  public Page<ClienteVagaProjection> buscarTodosPorClienteCpf(String cpf, Pageable pageable) {
      return repository.findAllByClienteCpf(cpf, pageable);
  }

  @Transactional(readOnly = true)
  public Page<ClienteVagaProjection> buscarTodosPorUsuarioId(Long id, Pageable pageable) {
    return repository.findAllByClienteUsuarioId(id, pageable);
  }

}
