package com.apirest.demo_park_api.exception;

public class VagaDisponivelException extends RuntimeException {

    // private String recusrso;
    // private String codigo;
    
    public VagaDisponivelException(String message) {
        super(message);
    }

    // public VagaDisponivelException(String message, String recusrso, String codigo) {
    //     super(message);
    //     this.recusrso = recusrso;
    //     this.codigo = codigo;
    // }

    
}
