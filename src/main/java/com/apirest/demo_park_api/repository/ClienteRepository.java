package com.apirest.demo_park_api.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.apirest.demo_park_api.entity.Cliente;
import com.apirest.demo_park_api.repository.projection.ClienteProjection;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    
@Query("select c from Cliente c")   
 Page<ClienteProjection> findAllPageable(Pageable pageable);
@Query
Cliente findByUsuarioId(Long id);
}