package com.example.desafio.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "cliente")
public class Cliente {
    @Getter @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Getter @Setter
    @Column(name = "nome")
    private String nome;

    @Getter @Setter
    @Column(name = "sobrenome")
    private String sobrenome;

    @Getter @Setter
    @Column(name = "cpf")
    private String CPF;

    @Getter @Setter
    @Column(name = "nascimento")
    private Date nascimento;

    @Getter @Setter
    @Column(name = "enderecos")
    private List<String> enderecos;
}
