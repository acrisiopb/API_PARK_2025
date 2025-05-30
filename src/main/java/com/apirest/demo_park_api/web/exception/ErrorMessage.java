package com.apirest.demo_park_api.web.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import jakarta.servlet.http.HttpServletRequest;
import lombok.*;

@Getter
@Setter
@ToString
public class ErrorMessage {

    private String path;
    private String method;
    private Integer status;
    private String statusText;
    private String message;

    private Map<String, String> errors;

    public ErrorMessage() {
    }

    public ErrorMessage(HttpServletRequest request, HttpStatus status, String message) {
        this.path = request.getRequestURI();
        this.method = request.getMethod();
        this.status = status.value();
        this.statusText = status.getReasonPhrase();
        this.message = message;
    }

    public ErrorMessage(HttpServletRequest request, HttpStatus status, String message, BindingResult result) {
        this.path = request.getRequestURI();
        this.method = request.getMethod();
        this.status = status.value();
        this.statusText = status.getReasonPhrase();
        this.message = message;
        addErrors(result);
    }

    /**
     * Adiciona os erros de validação capturados no objeto BindingResult a um mapa
     * local.
     * 
     * O método percorre todos os erros de campo (FieldError) presentes no
     * BindingResult
     * e os armazena em um mapa, onde a chave é o nome do campo com erro e o valor é
     * a mensagem de erro correspondente.
     *
     * Esse mapa pode ser usado para retornar mensagens de erro mais organizadas
     * e compreensíveis ao cliente da API.
     *
     * @param result o BindingResult que contém os erros de validação.
     */
    private void addErrors(BindingResult result) {
        this.errors = new HashMap<>();
        for (FieldError fieldError : result.getFieldErrors()) {
            this.errors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }
    }

}
