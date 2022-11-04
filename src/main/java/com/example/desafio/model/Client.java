package com.example.desafio.model;

import com.example.desafio.model.Address;
import lombok.*;

import javax.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDate;
import java.util.*;

@Entity
@Data
@Table
@EqualsAndHashCode
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "nome")
    private String name;

    @Column(name = "sobrenome")
    private String lastName;

    private String cpf;

    @Column(name = "data_nascimento")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDate;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE,
            CascadeType.REMOVE,
            CascadeType.REFRESH,
    })
    @JoinTable(name = "cliente_enderecos", joinColumns = @JoinColumn(name = "cliente_id"), inverseJoinColumns = @JoinColumn(name = "endereco_id"))
    private Set<Address> adresses = new HashSet<>();
}
