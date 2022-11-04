package com.example.desafio.utils;

import lombok.*;

@Data
@Builder
public class ApiError {

    private String menssage;
    private String status;
}
