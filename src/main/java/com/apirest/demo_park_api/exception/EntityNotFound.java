package com.apirest.demo_park_api.exception;

import lombok.Getter;

//RECIBO CHECKIN NOT FOUND EXCEPTION
@Getter
public class EntityNotFound extends RuntimeException {
    private String recurso;
    private String codigo;

    public EntityNotFound(String message) {
        super(message);
    }

    public EntityNotFound(String recurso, String codigo) {
        this.recurso = recurso;
        this.codigo = codigo;
    }

    

}
