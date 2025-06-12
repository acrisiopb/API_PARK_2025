package com.apirest.demo_park_api.service;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apirest.demo_park_api.entity.Vaga;
import com.apirest.demo_park_api.exception.CodigoUniqueViolationException;
import com.apirest.demo_park_api.exception.EntityNotFoundException;
import com.apirest.demo_park_api.repository.VagasRepository;

import lombok.*;

@RequiredArgsConstructor
@Service
public class VagaService {

    private final VagasRepository vagasRepository;

    @Transactional
    public Vaga salvar(Vaga vaga) {
        try {
            return vagasRepository.save(vaga);
        } catch (DataIntegrityViolationException ex) {
            throw new CodigoUniqueViolationException(
                    String.format("Vaga com codigo %s já cadastrada", vaga.getCodigo()));
        }
    }

    @Transactional(readOnly = true)
    public Vaga BuscarPorCodigo(String codigo) {
        return vagasRepository.findByCodigo(codigo).orElseThrow(
                () -> new EntityNotFoundException(String.format("Vaga com código %s não foram encontrado.", codigo)));
    }
    @Transactional(readOnly = true)
    public Vaga buscarPorVagaLivre() {
       return vagasRepository.findFirstByStatus(LIVRE).orElseThrow(
                () -> new EntityNotFoundException("Nenhuma vaga livre foi encontrado."));
    }

}
