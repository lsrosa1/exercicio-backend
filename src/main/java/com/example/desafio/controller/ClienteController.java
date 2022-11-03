package com.example.desafio.controller;

import com.example.desafio.model.Cliente;
import com.example.desafio.service.ClienteServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:8080/api")
@RestController
@RequestMapping("/cliente")
@RequiredArgsConstructor
public class ClienteController {


    private final ClienteServiceImpl clienteService;


    @GetMapping
    public ResponseEntity<?> listar() {
        List<Cliente> clientes = clienteService.listar();

        return ResponseEntity.ok().body(clientes);
    }

    @GetMapping("{id}")
    public ResponseEntity<?> obterPorId(@PathVariable Long id){
       Cliente cliente = clienteService.obterPorId(id);
        return ResponseEntity.ok().body(cliente);
    }


}
