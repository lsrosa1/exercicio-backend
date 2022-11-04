package com.example.desafio.controller;

import com.example.desafio.service.ClientService;
import com.example.desafio.model.Client;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.desafio.utils.ApiResponse;

import java.util.List;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/clientes")
@RequiredArgsConstructor
public class ClientController {

    private final ClientService clientService;

    @GetMapping
    public ResponseEntity<ApiResponse> list() {
        List<Client> clients = clientService.findAll();

        return ResponseEntity.ok(ApiResponse.builder().status(HttpStatus.OK.value()).menssage("")
                .data(clients).build());
    }

    @GetMapping("{id}")
    public ResponseEntity<ApiResponse> findById(@PathVariable Long id) {
        Client client = clientService.findById(id);
        return ResponseEntity.ok(ApiResponse.builder().status(HttpStatus.OK.value()).menssage("")
                .data(client).build());
    }

    @PostMapping
    public ResponseEntity<ApiResponse> create(@RequestBody @Valid Client client) {
        return ResponseEntity.status(HttpStatus.CREATED.value())
                .body(ApiResponse.builder().status(HttpStatus.CREATED.value()).menssage("Cliente criado com sucesso!")
                        .data(clientService.create(client)).build());
    }

    @DeleteMapping("{id}")
    public ResponseEntity<ApiResponse> remove(@PathVariable Long id) {
        return ResponseEntity
                .ok(ApiResponse.builder().status(HttpStatus.CREATED.value()).menssage("Cliente criado com sucesso!")
                        .data(clientService.remove(id)).build());
    }

    @PutMapping("{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Client newClient) {
        Client client = clientService.update(id, newClient);

        return ResponseEntity
                .ok(ApiResponse.builder().status(HttpStatus.CREATED.value()).menssage("Cliente criado com sucesso!")
                        .data(client).build());
    }
}