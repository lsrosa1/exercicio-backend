package com.example.desafio.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ApiResponse {

  private String menssage;
  private Object data;
  private int status;
}