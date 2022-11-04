package com.example.desafio.controller;

import com.example.desafio.exception.ClientNotFoundException;
import com.example.desafio.model.Client;
import com.example.desafio.service.ClientService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ClientControllerTest {

    private final String API_URL = "/api/clientes";
    @Autowired
    private MockMvc mvc;

    @MockBean
    private ClientService clientService;

    @Test
    @DisplayName("Deve buscar Cliente por id")
    @WithMockUser
    void shouldFindClientById() throws Exception {
        Client c1 = createClient();
        c1.setId(1l);

        when(clientService.findById(anyLong())).thenReturn(c1);
        String json = new ObjectMapper().writeValueAsString(c1);

        MockHttpServletRequestBuilder request = get(API_URL + "/" + c1.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("data.id").value(1l))
                .andExpect(jsonPath("data.name").value(c1.getName()));
    }

    @Test
    @DisplayName("Deve Retornar Not found quando cliente não existir")
    @WithMockUser
    void clientNotFound() throws Exception {
        Client c1 = createClient();
        c1.setId(1l);

        when(clientService.findById(anyLong())).thenThrow(new ClientNotFoundException());

        MockHttpServletRequestBuilder request = get(API_URL + "/" + c1.getId())
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
        c1.setId(1l);

        Client c2 = createClient();
        c2.setId(2l);

        List<Client> clients = Arrays.asList(c1, c2);

        when(clientService.findAll()).thenReturn(clients);
        String json = new ObjectMapper().writeValueAsString(clients);


        MockHttpServletRequestBuilder request = get(API_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("data").isArray());
    }


    @Test
    @DisplayName("Deve criar um Cliente")
    @WithMockUser
    void shouldCreateClient() throws Exception {
        Client client = createClient();

        Client clientSaved = createClient();
        clientSaved.setId(1l);

        String json = new ObjectMapper().writeValueAsString(client);

        when(clientService.create(any(Client.class))).thenReturn(clientSaved);

        MockHttpServletRequestBuilder request = post(API_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        mvc.perform(request)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("data.id").value(1))
                .andExpect(jsonPath("data.name").value(client.getName()));
    }

    @Test
    @DisplayName("Deve atualizar um Cliente")
    @WithMockUser
    void shouldUpdateClients() throws Exception {
        Client client = createClient();
        client.setId(1l);

        Client clientUpdated = createClient();
        clientUpdated.setId(1l);

        clientUpdated.setName("Joao");
        clientUpdated.setLastName("Alo");

        String json = new ObjectMapper().writeValueAsString(client);

        when(clientService.update(anyLong(), any(Client.class))).thenReturn(clientUpdated);

        MockHttpServletRequestBuilder request = put(API_URL + "/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("data.id").value(1))
                .andExpect(jsonPath("data.name").value(clientUpdated.getName()));
    }

    @Test
    @DisplayName("Deve remover um Cliente")
    @WithMockUser
    void shouldDeleteClientes() throws Exception {

        when(clientService.remove(anyLong())).thenReturn(Boolean.TRUE);

        MockHttpServletRequestBuilder request = delete(API_URL + "/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("data").value(Boolean.TRUE));
    }

    private Client createClient() {
        return Client.builder()
                .cpf("123456")
                .birthDate(new Date())
                .lastName("josé")
                .name("igor")
                .adresses(null)
                .build();
    }

}
