package com.example.desafio.model;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "administrador")
public class Administrador {
    @Getter @Setter
    @Column(name = "usuario")
    private static String Usuario;

    @Getter @Setter
    @Column(name = "senha")
    private static String Senha;

}
