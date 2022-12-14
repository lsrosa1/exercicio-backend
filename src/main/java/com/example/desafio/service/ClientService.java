package com.example.desafio.service;

import com.example.desafio.exception.ClientNotFoundException;

import com.example.desafio.exception.DuplicateCPFException;
import com.example.desafio.entity.Client;
import com.example.desafio.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository clientRepository;

    public List<Client> findAll() {
        return clientRepository.findAll();
    }

    public Optional<Client> findById(Long id) {
        return clientRepository.findById(id);
    }

    public Client create(Client client) {

        if(clientRepository.existsByCpf(client.getCpf())) {
            throw new DuplicateCPFException();
        }

        return clientRepository.save(client);
    }

    public void remove(Client client) {
        if(client == null || client.getId() == null) {
            throw new IllegalArgumentException("Id do cliente não pode ser null");
        }

        clientRepository.delete(client);
    }

    public Client update(Long id, Client newClient) {
        Client client = clientRepository.findById(id).orElseThrow(ClientNotFoundException::new);

        if (!Objects.equals(client.getCpf(), newClient.getCpf())) {
            if(clientRepository.existsByCpf(newClient.getCpf())) {
                throw new DuplicateCPFException();
            }
        }

        client.setCpf(newClient.getCpf());
        client.setBirthDate(newClient.getBirthDate());
        client.setAdresses(newClient.getAdresses());
        client.setName(newClient.getName());
        client.setLastName(newClient.getLastName());

        return clientRepository.save(client);
    }
}
