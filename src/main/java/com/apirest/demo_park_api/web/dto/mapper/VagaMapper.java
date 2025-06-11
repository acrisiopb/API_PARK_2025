package com.apirest.demo_park_api.web.dto.mapper;

import org.modelmapper.ModelMapper;

import com.apirest.demo_park_api.entity.Vaga;
import com.apirest.demo_park_api.web.dto.VagaCreateDTO;
import com.apirest.demo_park_api.web.dto.VagaResponseDto;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class VagaMapper {
    
    public static Vaga toVaga(VagaCreateDTO dto){
       return new ModelMapper().map(dto, Vaga.class);
    }

    public static VagaResponseDto toDto(Vaga vaga){
        return new ModelMapper().map(vaga, VagaResponseDto.class);
    }

}
