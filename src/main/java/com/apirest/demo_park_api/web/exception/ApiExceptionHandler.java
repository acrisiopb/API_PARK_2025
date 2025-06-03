package com.apirest.demo_park_api.web.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.apirest.demo_park_api.exception.EntityNotFoundException;
import com.apirest.demo_park_api.exception.PasswordInvalidException;
import com.apirest.demo_park_api.exception.UsernameUniqueViolationException;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

// Anotação do Lombok que gera automaticamente um logger (log) para a classe
@Slf4j
@RestControllerAdvice // Indica que esta classe trata exceções de forma global para os controladores
                      // REST
public class ApiExceptionHandler {

        /**
         * Trata exceções do tipo MethodArgumentNotValidException,
         * que ocorrem quando os dados de entrada não passam nas validações dos DTOs.
         * 
         * Exemplo: um campo anotado com @NotNull ou @Size é enviado nulo ou com tamanho
         * inválido.
         *
         * @param ex      a exceção capturada
         * @param request o objeto HttpServletRequest que contém os detalhes da
         *                requisição
         * @param result  o BindingResult contendo os detalhes dos erros de validação
         * @return ResponseEntity com status 422 (Unprocessable Entity) e mensagem de
         *         erro formatada
         */
        @ExceptionHandler(MethodArgumentNotValidException.class)
        public ResponseEntity<ErrorMessage> methodArgumentNotValidException(MethodArgumentNotValidException ex,
                        HttpServletRequest request,
                        BindingResult result) {

                log.error("Api Error - ", ex); // Loga o erro para facilitar o rastreamento

                return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY) // HTTP 422 - Entidade não processável
                                .contentType(MediaType.APPLICATION_JSON) // Define o tipo de retorno como JSON
                                .body(new ErrorMessage( // Cria e retorna um objeto de erro personalizado
                                                request,
                                                HttpStatus.UNPROCESSABLE_ENTITY,
                                                "Campo(s) inválido(s)", // Mensagem genérica de erro
                                                result // Passa o BindingResult para coletar os erros de validação
                                ));
        }

        @ExceptionHandler(EntityNotFoundException.class)
        public ResponseEntity<ErrorMessage> entityNotFoundException(EntityNotFoundException ex,
                        HttpServletRequest request) {

                log.error("Api Error - ", ex);

                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                .contentType(MediaType.APPLICATION_JSON)
                                .body(new ErrorMessage(
                                                request,
                                                HttpStatus.NOT_FOUND,
                                                ex.getMessage()));
        }

        @ExceptionHandler(UsernameUniqueViolationException.class)
        public ResponseEntity<ErrorMessage> usernameUniqueViolationException(RuntimeException ex,
                        HttpServletRequest request) {

                log.error("Api Error - ", ex);

                return ResponseEntity.status(HttpStatus.CONFLICT)
                                .contentType(MediaType.APPLICATION_JSON)
                                .body(new ErrorMessage(request,
                                                HttpStatus.CONFLICT, ex.getMessage()));
        }

        @ExceptionHandler(PasswordInvalidException.class)
        public ResponseEntity<ErrorMessage> passwordInvalidException(RuntimeException ex, HttpServletRequest request) {
                log.error("Api Error - ", ex);
                return ResponseEntity
                                .status(HttpStatus.BAD_REQUEST)
                                .contentType(MediaType.APPLICATION_JSON)
                                .body(new ErrorMessage(request, HttpStatus.BAD_REQUEST, ex.getMessage()));
        }

}
