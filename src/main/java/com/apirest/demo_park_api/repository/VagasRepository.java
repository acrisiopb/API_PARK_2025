package com.apirest.demo_park_api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.apirest.demo_park_api.entity.Vaga;

public interface VagasRepository extends JpaRepository<Vaga,Long> {

    Optional<Vaga> findByCodigo(String codigo);
    
   Optional<Vaga> findFirstByStatus(Vaga.statusVaga statusVaga);
}
