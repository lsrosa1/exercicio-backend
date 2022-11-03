package com.example.desafio.controller;

import com.example.desafio.model.Cliente;
import com.example.desafio.service.ClienteServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
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

    @PostMapping
    public  ResponseEntity<?> criar(@RequestBody Cliente cliente) {
        clienteService.criar(cliente);
        return ResponseEntity.status(HttpStatus.CREATED).body(null);
    }


    @DeleteMapping("{id}")
    public ResponseEntity<?> remover(@PathVariable Long id) {
        clienteService.remover(id);
        return ResponseEntity.ok().body(null);
    }

    @PutMapping("{id}")
    public ResponseEntity<?> atualizar(@PathVariable Long id, @RequestBody Cliente novoCliente) {
        Cliente cliente = clienteService.atualizar(id, novoCliente);
        return ResponseEntity.ok().body(cliente);
    }
}
