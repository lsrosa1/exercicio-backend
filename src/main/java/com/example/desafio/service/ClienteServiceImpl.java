package com.example.desafio.service;

import com.example.desafio.exception.ClienteNotFoundException;
import com.example.desafio.model.Cliente;
import com.example.desafio.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClienteServiceImpl implements ClienteService {

    private final ClienteRepository clienteRepository;

    @Override
    public List<Cliente> listar(){
        return clienteRepository.findAll();
    }

    @Override
    public Cliente obterPorId(Long id) {
        return clienteRepository.findById(id).orElseThrow(ClienteNotFoundException::new);
    }

    @Override
    public void criar(Cliente cliente) {
        try {
            clienteRepository.save(cliente);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao tentar criar Cliente");
        }
    }

    @Override
    public void remover(Long id) {
        clienteRepository.findById(id).orElseThrow(ClienteNotFoundException::new);
        clienteRepository.deleteById(id);
    }

    @Override
    public Cliente atualizar(Long id, Cliente novoCliente) {
        return clienteRepository.findById(id).map(c -> {
                c.setCpf(novoCliente.getCpf());
                c.setNome(novoCliente.getNome());
                c.setSobrenome(novoCliente.getSobrenome());
                c.setDataNascimento(novoCliente.getDataNascimento());
                c.setEnderecos(novoCliente.getEnderecos());
                return clienteRepository.save(c);
            }).orElseThrow(ClienteNotFoundException::new);


    }
}
