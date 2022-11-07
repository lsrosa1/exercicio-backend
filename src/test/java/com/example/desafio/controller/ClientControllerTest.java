package com.example.desafio.controller;

import com.example.desafio.exception.DuplicateCPFException;
import com.example.desafio.model.Address;
import com.example.desafio.model.Client;
import com.example.desafio.service.ClientService;
import com.example.desafio.utils.GsonLocalDateAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.time.LocalDate;
import java.time.Month;
import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ClientControllerTest {

    private final String API_URL = "/api/clientes/";

    private final Gson gson = new GsonBuilder().setPrettyPrinting().registerTypeAdapter(LocalDate.class, new GsonLocalDateAdapter()).create();
    @Autowired
    private MockMvc mvc;
    @MockBean
    private ClientService clientService;

    @Test
    @DisplayName("Deve buscar Cliente por id")
    @WithMockUser
    void shouldFindClientById() throws Exception {
        Client client = createClient();
        client.setId(1L);

        when(clientService.findById(anyLong())).thenReturn(Optional.of(client));
        String json = gson.toJson(client);

        MockHttpServletRequestBuilder request = get(API_URL + client.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(1L))
                .andExpect(jsonPath("$.data.name").value(client.getName()))
                .andExpect(jsonPath("$.data.lastName").value(client.getLastName()))
                .andExpect(jsonPath("$.data.cpf").value(client.getCpf()))
                .andExpect(jsonPath("$.data.adresses").isArray())
                .andExpect(jsonPath("$.data.birthDate").value(client.getBirthDate().toString()));
    }

    @Test
    @DisplayName("Error quando cliente não existir")
    @WithMockUser
    void errorClientNotFound() throws Exception {
        Client client = createClient();
        client.setId(1L);

        when(clientService.findById(anyLong())).thenReturn(Optional.empty());

        MockHttpServletRequestBuilder request = get(API_URL + client.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        mvc.perform(request)
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Deve listar todos os Clientes")
    @WithMockUser
    void shouldListAllClients() throws Exception {
        Client c1 = createClient();
        c1.setId(1L);

        Client c2 = createClient();
        c2.setId(2L);

        List<Client> clients = Arrays.asList(c1, c2);

        when(clientService.findAll()).thenReturn(clients);

        MockHttpServletRequestBuilder request = get(API_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").isArray());
    }


    @Test
    @DisplayName("Deve criar um Cliente")
    @WithMockUser
    void shouldCreateClient() throws Exception {
        Client client = createClient();

        Client clientSaved = createClient();
        clientSaved.setId(1L);

        String json = gson.toJson(client);

        when(clientService.create(any(Client.class))).thenReturn(clientSaved);

        MockHttpServletRequestBuilder request = post(API_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        mvc.perform(request)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.id").value(clientSaved.getId()))
                .andExpect(jsonPath("$.data.lastName").value(clientSaved.getLastName()))
                .andExpect(jsonPath("$.data.name").value(clientSaved.getName()))
                .andExpect(jsonPath("$.data.adresses").isArray())
                .andExpect(jsonPath("$.data.birthDate").value(clientSaved.getBirthDate().toString()))
                .andExpect(jsonPath("$.data.cpf").value(clientSaved.getCpf()));
    }

    @Test
    @DisplayName("Erro ao tentar criar Cliente com CPF duplicado")
    @WithMockUser
    void errorWhenCPFDuplicated() throws Exception {

        Client client = createClient();
        String json = gson.toJson(client);
        when(clientService.create(any(Client.class))).thenThrow(new DuplicateCPFException());

        MockHttpServletRequestBuilder request = post(API_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        mvc.perform(request)
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Deve atualizar um Cliente")
    @WithMockUser
    void shouldUpdateClients() throws Exception {
        Client client = createClient();
        client.setId(1L);

        Client clientUpdated = createClient();
        clientUpdated.setId(1L);
        clientUpdated.setName("Joao");
        clientUpdated.setLastName("Alo");

        String json = gson.toJson(client);

        when(clientService.update(anyLong(), any(Client.class))).thenReturn(clientUpdated);

        MockHttpServletRequestBuilder request = put(API_URL + client.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(clientUpdated.getId()))
                .andExpect(jsonPath("$.data.lastName").value(clientUpdated.getLastName()))
                .andExpect(jsonPath("$.data.name").value(clientUpdated.getName()))
                .andExpect(jsonPath("$.data.adresses").isArray())
                .andExpect(jsonPath("$.data.birthDate").value(clientUpdated.getBirthDate().toString()))
                .andExpect(jsonPath("$.data.cpf").value(clientUpdated.getCpf()));
    }

    @Test
    @DisplayName("Deve remover um Cliente")
    @WithMockUser
    void shouldRemoveClients() throws Exception {
        long id = 1L;
        Client client = createClient();
        client.setId(id);

        when(clientService.findById(anyLong())).thenReturn(Optional.of(client));

        MockHttpServletRequestBuilder request = delete(API_URL + id)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").value(true));
    }

    @Test
    @DisplayName("Erro ao tentar remover um Cliente inexistente")
    @WithMockUser
    void shouldGiveErrorWhenRemoveClientNotFound() throws Exception {
        long id = 1L;
        Client client = createClient();
        client.setId(id);

        when(clientService.findById(anyLong())).thenReturn(Optional.empty());

        MockHttpServletRequestBuilder request = delete(API_URL + id)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        mvc.perform(request)
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    private Client createClient() {
        Address address2 = Address.builder().city("Natal").neighborhood("Ponta Negra").zipCode("59101200").number("1820").street("Av. Roberto Freire").build();

        Set<Address> adresses = new HashSet<>();
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
