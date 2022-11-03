package com.example.desafio.repository;

import com.example.desafio.model.Administrador;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface AdministradorRepository extends CrudRepository<Administrador, Long> {
    List<Administrador> findById(long id);
}
