package com.example.desafio.repository;

import com.example.desafio.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    Boolean existsByCpf(String cpf);
}
