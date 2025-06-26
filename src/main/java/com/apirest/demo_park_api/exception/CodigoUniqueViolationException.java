package com.apirest.demo_park_api.exception;

import lombok.*;

@Getter 
public class CodigoUniqueViolationException extends RuntimeException {

    private String recurso;
    private String codigo;

    public CodigoUniqueViolationException(String message) {
        super(message);
    }

    public CodigoUniqueViolationException(String recurso, String codigo){
        this.recurso = recurso;
        this.codigo = codigo;
    }
}
