package com.example.desafio.utils;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ApiResponse {

  private String menssage;
  private Object data;
  private int status;
}