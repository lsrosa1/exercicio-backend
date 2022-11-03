package com.example.desafio.service;

import com.example.desafio.model.Cliente;
import com.example.desafio.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClienteServiceImpl implements ClienteService {

    private final ClienteRepository clienteRepository;

    @Override
    public List<Cliente> listar() {
        return null;
    }

    @Override
    public Cliente obterPorId(Long id) {
        return clienteRepository.findById(id).get();
    }

    @Override
    public void criar(Cliente cliente) {

    }

    @Override
    public void remover(Long id) {

    }

    @Override
    public Optional<Cliente> atualizar(Cliente cliente) {
        return Optional.empty();
    }
}
