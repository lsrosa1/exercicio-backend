package com.example.desafio.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ClienteNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ApiError handlerNoClienteFoundException(ClienteNotFoundException ex) {
        return ApiError.builder().menssage("Cliente Não foi encontrado").status(String.valueOf(HttpStatus.NOT_FOUND.value())).build();
    }
    @ExceptionHandler(InvalidCredentialsException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ApiError handlerNoClienteFoundException(InvalidCredentialsException ex) {
        return ApiError.builder().menssage("Credenciais Invalidas").status(String.valueOf(HttpStatus.NOT_FOUND.value())).build();
    }

}
