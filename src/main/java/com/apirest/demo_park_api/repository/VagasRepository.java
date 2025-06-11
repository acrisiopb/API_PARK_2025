package com.apirest.demo_park_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.apirest.demo_park_api.entity.Vaga;

public interface VagasRepository extends JpaRepository<Vaga,Long> {

}
