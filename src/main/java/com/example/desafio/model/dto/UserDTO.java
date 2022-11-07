package com.example.desafio.model.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@Builder
public class UserDTO {

    @NotNull
    private String login;

    @NotNull
    private String password;
}
