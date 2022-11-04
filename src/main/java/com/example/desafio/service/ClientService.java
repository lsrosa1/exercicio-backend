package com.example.desafio.service;

import com.example.desafio.exception.ClientNotFoundException;

import com.example.desafio.model.Client;
import com.example.desafio.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository clientRepository;

    public List<Client> findAll() {
        return clientRepository.findAll();
    }

    public Client findById(Long id) {
        return clientRepository.findById(id).orElseThrow(ClientNotFoundException::new);
    }

    public Client create(Client cliente) {
        try {
            return clientRepository.save(cliente);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao tentar criar Cliente");
        }
    }

    public Boolean remove(Long id) {
        clientRepository.findById(id).orElseThrow(ClientNotFoundException::new);
        clientRepository.deleteById(id);
        return Boolean.TRUE;
    }

    public Client update(Long id, Client newClient) {
        return clientRepository.findById(id).map(client -> {
            client.setCpf(newClient.getCpf());
            client.setBirthDate(newClient.getBirthDate());
            client.setAdresses(newClient.getAdresses());
            client.setName(newClient.getName());
            client.setLastName(newClient.getLastName());
            return clientRepository.save(client);
        }).orElseThrow(ClientNotFoundException::new);
    }
}
