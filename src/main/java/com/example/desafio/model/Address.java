package com.example.desafio.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "endereco")
@Data
@Builder
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "rua")
    private String street;

    @Column(name = "cidade")
    private String city;

    @Column(name = "bairro")
    private String neighborhood;

    @Column(name = "numero")
    private String number;

    @Column(name = "cep")
    private String zipCode;

    @ManyToMany(mappedBy = "adresses", fetch = FetchType.LAZY, cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE,
            CascadeType.REFRESH,
    })
    @JsonIgnore
    private Set<Client> clients = new HashSet<>();
}
