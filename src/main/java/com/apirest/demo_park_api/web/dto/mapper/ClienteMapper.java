package com.apirest.demo_park_api.web.dto.mapper;

import org.modelmapper.ModelMapper;
import com.apirest.demo_park_api.entity.Cliente;
import com.apirest.demo_park_api.web.dto.ClienteCreateDTO;
import com.apirest.demo_park_api.web.dto.ClienteResponseDto;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ClienteMapper {
    public static Cliente toCliente(ClienteCreateDTO dto){
        return new ModelMapper().map(dto, Cliente.class);
    }
   
     public static ClienteResponseDto toDto(Cliente cliente){
        return new ModelMapper().map(cliente, ClienteResponseDto.class);
    }
    

}
