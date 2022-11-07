package com.example.desafio.client;

import com.example.desafio.exception.DuplicateCPFException;
import com.example.desafio.model.Address;
import com.example.desafio.model.Client;
import com.example.desafio.repository.ClientRepository;
import com.example.desafio.service.ClientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.time.Month;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

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
    @DisplayName("Deve criar um Cliente")
    public void shouldCreateNewClient() {

        Client clientMock = createClient();
        clientMock.setId(1L);
        when(clientRepository.save(any(Client.class))).thenReturn(clientMock);

        Client client = createClient();
        Client clientSaved = clientRepository.save(client);

        assertEquals(clientSaved.getId(), clientMock.getId());
        assertEquals(clientSaved.getCpf(), clientMock.getCpf());
        assertEquals(clientSaved.getBirthDate(), clientMock.getBirthDate());
        assertEquals(clientSaved.getLastName(), clientMock.getLastName());
        assertEquals(clientSaved.getName(), clientMock.getName());
        assertEquals(clientSaved.getAdresses().size(), 2);
    }

    @Test
    @DisplayName("Erro ao tentar salvar Cliente com CPF duplicado")
    public void shouldGiveErrorWhenCPFDuplicated() {

        when(clientRepository.existsByCpf(anyString())).thenReturn(true);

        Client client = createClient();
        DuplicateCPFException exception = assertThrows(DuplicateCPFException.class, () -> {clientService.create(client);});

        assertEquals(DuplicateCPFException.class, exception.getClass());
        verify(clientRepository, never()).save(client);
    }

    @Test
    @DisplayName("Deve selecionar um Cliente especifico")
    public void shouldSelectSpecificClient() {

        Client clientMock = createClient();
        clientMock.setId(1L);
        when(clientRepository.findById(anyLong())).thenReturn(Optional.of(clientMock));

        Optional<Client> clientSearched = clientService.findById(clientMock.getId());

        assertEquals(clientSearched.isPresent(), true);
        assertEquals(clientSearched.get().getId(), clientMock.getId());
        assertEquals(clientSearched.get().getCpf(), clientMock.getCpf());
        assertEquals(clientSearched.get().getBirthDate(), clientMock.getBirthDate());
        assertEquals(clientSearched.get().getLastName(), clientMock.getLastName());
        assertEquals(clientSearched.get().getName(), clientMock.getName());
        assertEquals(clientSearched.get().getAdresses().size(), 2);
    }

    @Test
    @DisplayName("Erro ao tentar selecionar um Cliente que não existe")
    public void shouldGiveErrorWhenClientNotFound() {
        when(clientRepository.findById(anyLong())).thenReturn(Optional.empty());

        Long id = 1L;
        Optional<Client> optionalClient = clientService.findById(id);

        assertFalse(optionalClient.isPresent());
    }

    @Test
    @DisplayName("Deve listar os Clientes")
    public void shouldListAllClients() {

        Client client1 = createClient();
        client1.setId(1L);
        Client client2 = createClient();
        client1.setId(1L);
        List<Client> clientsMock = Arrays.asList(client1, client2);
        when(clientRepository.findAll()).thenReturn(clientsMock);

        List<Client> clientsFound = clientService.findAll();

        assertEquals(clientsFound.size(), clientsMock.size());
    }

    @Test
    @DisplayName("Erro ao tentar remover Cliente com ID inexistente")
    public void shouldGiveErrorWhenRemoveClientNotFound() {
        Client client = new Client();

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {clientService.remove(client);});

        assertEquals(IllegalArgumentException.class, exception.getClass());
        verify(clientRepository, times(0)).delete(client);
    }

    @Test
    @DisplayName("Deve remover um Cliente")
    public void shouldRemoveClient() {
        Client client = createClient();
        client.setId(1L);

        assertDoesNotThrow(() -> clientService.remove(client));

        verify(clientRepository, times(1)).delete(client);
    }


    private Client createClient() {
        Address address1 = Address.builder().city("Parnamirim").neighborhood("Monte Castelo").zipCode("59132123").number("170").street("Av. Abel Cabral").build();
        Address address2 = Address.builder().city("Natal").neighborhood("Ponta Negra").zipCode("59101200").number("1820").street("Av. Roberto Freire").build();

        Set<Address> adresses = new HashSet<>();
        adresses.add(address1);
        adresses.add(address2);

        return Client.builder()
                .cpf("123456")
                .birthDate(LocalDate.of(2000, Month.MAY, 12))
                .lastName("josé")
                .name("igor")
                .adresses(adresses)
                .build();
    }

}
