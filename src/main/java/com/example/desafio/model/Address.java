package com.example.desafio.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "endereco")
@Data
@Builder
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Address address = (Address) o;

        if (!Objects.equals(id, address.id)) return false;
        if (!Objects.equals(street, address.street)) return false;
        if (!Objects.equals(city, address.city)) return false;
        if (!Objects.equals(neighborhood, address.neighborhood))
            return false;
        if (!Objects.equals(number, address.number)) return false;
        return Objects.equals(zipCode, address.zipCode);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (street != null ? street.hashCode() : 0);
        result = 31 * result + (city != null ? city.hashCode() : 0);
        result = 31 * result + (neighborhood != null ? neighborhood.hashCode() : 0);
        result = 31 * result + (number != null ? number.hashCode() : 0);
        result = 31 * result + (zipCode != null ? zipCode.hashCode() : 0);
        return result;
    }
}
