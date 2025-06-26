package com.apirest.demo_park_api.service;


import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apirest.demo_park_api.entity.Vaga;
import static com.apirest.demo_park_api.entity.Vaga.StatusVaga.LIVRE;

import com.apirest.demo_park_api.exception.CodigoUniqueViolationException;
import com.apirest.demo_park_api.exception.EntityNotFoundException;
import com.apirest.demo_park_api.exception.VagaDisponivelException;
import com.apirest.demo_park_api.repository.VagasRepository;

@RequiredArgsConstructor
@Service
public class VagaService {

    private final VagasRepository vagaRepository;

    @Transactional
    public Vaga salvar(Vaga vaga) {
        try {
            return vagaRepository.save(vaga);
        } catch (DataIntegrityViolationException ex) {
            throw new CodigoUniqueViolationException("Vaga" , vaga.getCodigo());
        }
    }

    @Transactional(readOnly = true)
    public Vaga buscarPorCodigo(String codigo) {
        return vagaRepository.findByCodigo(codigo).orElseThrow(
                () -> new EntityNotFoundException("Vaga", codigo)
                );
        
    }

    @Transactional(readOnly = true)
    public Vaga buscarPorVagaLivre() {
        return vagaRepository.findFirstByStatus(LIVRE).orElseThrow(
                () -> new VagaDisponivelException("Vaga", "LIVRE")
        );
    }
}