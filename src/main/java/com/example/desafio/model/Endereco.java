package com.example.desafio.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "endereco")
@Data
public class Endereco {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String rua;
    private String cidade;
    private String bairro;
    private String numero;
    private String cep;
}
