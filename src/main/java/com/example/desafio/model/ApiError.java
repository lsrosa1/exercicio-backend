package com.example.desafio.model;

import lombok.*;

@Data
@Builder
public class ApiError {

    private String menssage;
    private String status;
}
