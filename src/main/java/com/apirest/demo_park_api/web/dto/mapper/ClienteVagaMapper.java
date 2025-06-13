package com.apirest.demo_park_api.web.dto.mapper;

import org.modelmapper.ModelMapper;

import com.apirest.demo_park_api.entity.ClienteVaga;
import com.apirest.demo_park_api.web.dto.EstacionamentoCreateDto;
import com.apirest.demo_park_api.web.dto.EstacionamentoResponseDto;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ClienteVagaMapper {

    public static ClienteVaga toClienteVaga(EstacionamentoCreateDto dto) {
        return new ModelMapper().map(dto, ClienteVaga.class);
    }

    public static EstacionamentoResponseDto toDto(ClienteVaga clienteVaga) {
        return new ModelMapper().map(clienteVaga, EstacionamentoResponseDto.class);
    }
}
