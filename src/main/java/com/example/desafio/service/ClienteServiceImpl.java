package com.example.desafio.service;

import com.example.desafio.model.Cliente;
import com.example.desafio.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClienteServiceImpl implements ClienteService {

    private final ClienteRepository clienteRepository;
    private final EnderecoRepository enderecoRepository;

    @Override
    public List<Cliente> listar(){
        List<Cliente> clientes = new ArrayList<>();
        clienteRepository.findAll().forEach(clientes::add);
        return clientes;
    }

    @Override
    public Cliente obterPorId(Long id) {
        return clienteRepository.findById(id).orElseThrow(() -> new RuntimeException("Cliente não foi encontrado"));
    }

    @Override
    public void criar(Cliente cliente) {
        try {
            clienteRepository.save(cliente);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("Erro ao tentar criar Cliente");
        }
    }

    @Override
    public void remover(Long id) {
        Optional<Cliente> cliente = clienteRepository.findById(id);

        if(!cliente.isPresent()) {
            throw new RuntimeException("Cliente não foi encontrado");
        }

        clienteRepository.deleteById(id);
    }

    @Override
    public Cliente atualizar(Long id, Cliente novoCliente) {

        try {
            return clienteRepository.findById(id).map(c -> {
                c.setCpf(novoCliente.getCpf());
                c.setNome(novoCliente.getNome());
                c.setSobrenome(novoCliente.getSobrenome());
                c.setDataNascimento(novoCliente.getDataNascimento());

                return clienteRepository.save(c);
            }).orElseThrow(() -> new RuntimeException("Cliente não foi encontrado"));

        } catch (Exception e) {
            throw new RuntimeException("Erro ao tentar atualizar Cliente");
        }

    }
}
