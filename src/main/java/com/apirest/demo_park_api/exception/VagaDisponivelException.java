package com.apirest.demo_park_api.exception;

import lombok.Getter;

@Getter
public class VagaDisponivelException extends RuntimeException {

    private String recurso;
    private String codigo;
    
    public VagaDisponivelException(String message) {
        super(message);
    }

    public VagaDisponivelException(String recurso, String codigo) {
        this.recurso = recurso;
        this.codigo = codigo;
    }

   

    
}
