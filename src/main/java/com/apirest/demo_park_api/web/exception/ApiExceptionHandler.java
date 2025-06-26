package com.apirest.demo_park_api.web.exception;

import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.apirest.demo_park_api.exception.CodigoUniqueViolationException;
import com.apirest.demo_park_api.exception.EntityNotFound;
import com.apirest.demo_park_api.exception.EntityNotFoundException;
import com.apirest.demo_park_api.exception.PasswordInvalidException;
import com.apirest.demo_park_api.exception.UsernameUniqueViolationException;
import com.apirest.demo_park_api.exception.VagaDisponivelException;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

// Anotação do Lombok que gera automaticamente um logger (log) para a classe
@Slf4j
@RequiredArgsConstructor
@RestControllerAdvice // Indica que esta classe trata exceções de forma global para os controladores
                      // REST
public class ApiExceptionHandler {

        private final MessageSource messageSource;

        @ExceptionHandler(EntityNotFoundException.class)
        public ResponseEntity<ErrorMessage> entityNotFoundException(EntityNotFoundException ex,
                        HttpServletRequest request) {
                Object[] params = new Object[] { ex.getRecurso(), ex.getCodigo() };
                String message = messageSource.getMessage("exception.entityNotFoundException", params,
                                request.getLocale());
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                .contentType(MediaType.APPLICATION_JSON)
                                .body(new ErrorMessage(
                                                request,
                                                HttpStatus.NOT_FOUND,
                                                message));
        }
        @ExceptionHandler(EntityNotFound.class)
        public ResponseEntity<ErrorMessage> entityNotFound(EntityNotFound ex,
                        HttpServletRequest request) {
                Object[] params = new Object[] { ex.getRecurso(), ex.getCodigo() };
                String message = messageSource.getMessage("exception.entityNotFound             ", params,
                                request.getLocale());
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                .contentType(MediaType.APPLICATION_JSON)
                                .body(new ErrorMessage(
                                                request,
                                                HttpStatus.NOT_FOUND,
                                                message));
        }

        @ExceptionHandler(CodigoUniqueViolationException.class)
        public ResponseEntity<ErrorMessage> codigoUniqueViolationException(CodigoUniqueViolationException ex,
                        HttpServletRequest request) {
                Object[] params = new Object[] { ex.getRecurso(), ex.getCodigo() };
                String message = messageSource.getMessage("exception.codigoUniqueViolationException", params,
                                request.getLocale());
                return ResponseEntity.status(HttpStatus.CONFLICT)
                                .contentType(MediaType.APPLICATION_JSON)
                                .body(new ErrorMessage(request,
                                                HttpStatus.CONFLICT, message));
        }

        @ExceptionHandler(VagaDisponivelException.class)
        public ResponseEntity<ErrorMessage> vagaDisponivelException(VagaDisponivelException ex,
                        HttpServletRequest request) {
                String message = messageSource.getMessage("exception.vagaDisponivelException", null,
                                request.getLocale());
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                .contentType(MediaType.APPLICATION_JSON)
                                .body(new ErrorMessage(
                                                request,
                                                HttpStatus.NOT_FOUND,
                                                message));
        }

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
                                                messageSource.getMessage("message.invalid.field", null,
                                                                request.getLocale()), // Mensagem genérica de erro
                                                result // Passa o BindingResult para coletar os erros de validação
                                                , messageSource));
        }

        @ExceptionHandler({ UsernameUniqueViolationException.class, CpfUniqueViolationException.class })
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

        @ExceptionHandler(AccessDeniedException.class)
        public ResponseEntity<ErrorMessage> accessDeniedException(AccessDeniedException ex,
                        HttpServletRequest request) {
                log.error("Api Error - ", ex);
                return ResponseEntity
                                .status(HttpStatus.FORBIDDEN)
                                .contentType(MediaType.APPLICATION_JSON)
                                .body(new ErrorMessage(request, HttpStatus.FORBIDDEN, ex.getMessage()));
        }

        @ExceptionHandler(Exception.class)
        public ResponseEntity<ErrorMessage> internalServerException(Exception ex,
                        HttpServletRequest request) {
                ErrorMessage error = new ErrorMessage(
                                request, HttpStatus.INTERNAL_SERVER_ERROR,
                                HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());

                log.error("Internal server Error {} {}", error, ex.getMessage()); // Loga o erro para facilitar o
                                                                                  // rastreamento

                return ResponseEntity
                                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                                .contentType(MediaType.APPLICATION_JSON)
                                .body(error);
        }

}
