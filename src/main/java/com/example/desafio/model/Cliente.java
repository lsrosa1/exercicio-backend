package com.example.desafio.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Table
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String nome;

    private String sobrenome;

    private String cpf;

    @Column(name = "data_nascimento")
    private LocalDate dataNascimento;

    @OneToMany
    private Set<Endereco> enderecos = new HashSet<Endereco>();
}
