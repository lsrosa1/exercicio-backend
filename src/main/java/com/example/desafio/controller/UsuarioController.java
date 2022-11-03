package com.example.desafio.controller;

import com.example.desafio.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:8080/api")
@RestController
@RequestMapping("/administrador")
public class AdministradorController {

    @Autowired
    UsuarioRepository administradorRepository;
}
