package com.example.desafio.model.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class UsuarioDTO {

    @NotNull
    private String login;

    @NotNull
    private String senha;
}
