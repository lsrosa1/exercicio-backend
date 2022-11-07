package com.example.desafio.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.desafio.utils.ApiError;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ClientNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ApiError handlerNoClientFoundException(ClientNotFoundException ex) {
        return ApiError.builder().menssage("Cliente Não foi encontrado")
                .status(String.valueOf(HttpStatus.NOT_FOUND.value())).build();
    }

    @ExceptionHandler(DuplicateCPFException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ApiError handlerDuplicateCPFException(DuplicateCPFException ex) {
        return ApiError.builder().menssage("CPF duplicado" )
                .status(String.valueOf(HttpStatus.BAD_REQUEST.value())).build();
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ApiError handlerNoClientFoundException(InvalidCredentialsException ex) {
        return ApiError.builder().menssage("Credenciais Invalidas").status(String.valueOf(HttpStatus.NOT_FOUND.value()))
                .build();
    }

}
