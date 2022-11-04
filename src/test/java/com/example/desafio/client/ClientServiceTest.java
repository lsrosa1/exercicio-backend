package com.example.desafio.client;


import com.example.desafio.model.Client;
import com.example.desafio.repository.ClientRepository;
import com.example.desafio.service.ClientService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.time.Month;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
public class ClientServiceTest {

    @Mock
    private ClientRepository clientRepository;

    private ClientService clientService;

    @BeforeEach
    void initClientService() {
        clientService = new ClientService(clientRepository);
    }

    @Test
    @DisplayName("Deve salvar um Cliente")
    public void shouldSaveClient() {
        Client client = Client.builder().cpf("123456").birthDate(LocalDate.of(2000, Month.MAY, 12)).lastName("josé").name("igor").adresses(null).build();

        Mockito.when(clientRepository.save(client)).thenReturn(
                Client.builder().id(1).cpf("123456").birthDate(LocalDate.of(2000, Month.MAY, 12)).lastName("josé").name("igor").adresses(null).build()
        );

        Client clientSaved = clientService.create(client);

        Assertions.assertEquals(clientSaved.getId(), 1);
        Assertions.assertEquals(clientSaved.getCpf(), "123456");
        Assertions.assertEquals(clientSaved.getBirthDate(), LocalDate.of(2000, Month.MAY, 12));
        Assertions.assertEquals(clientSaved.getLastName(), "josé");
        Assertions.assertEquals(clientSaved.getName(), "igor");
        Assertions.assertNull(clientSaved.getAdresses());
    }

}
