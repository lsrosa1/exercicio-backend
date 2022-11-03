package com.example.desafio.service;

import com.example.desafio.model.Cliente;

import java.util.List;
import java.util.Optional;

public interface ClienteService {
    List<Cliente> listar();
    Cliente obterPorId(Long id);
    void criar(Cliente cliente);
    void remover(Long id);
    Cliente atualizar(Long id, Cliente cliente);
}
