package com.example.desafio.model;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "administrador")
@Getter
@Setter
public class Usuario {

    @Column(name = "usuario")
    private static String login;


    @Column(name = "senha")
    private static String senha;

}
