package com.example.desafio.model.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class UserDTO {

    @NotNull
    private String login;

    @NotNull
    private String senha;
}
