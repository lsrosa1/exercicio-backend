package com.example.desafio.controller;

import com.example.desafio.model.Client;
import com.example.desafio.service.ClientService;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Date;

import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ClientControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ClientService clientService;

    @Test
    @WithMockUser
    void shouldCreateClient() throws Exception {
        Client client = Client.builder()
                .cpf("123456")
                .birthDate(new Date())
                .lastName("josé")
                .name("igor")
                .adresses(null)
                .build();

        Client clientSaved = Client.builder()
                .id(1)
                .cpf("123456")
                .birthDate(new Date())
                .lastName("josé")
                .name("igor")
                .adresses(null)
                .build();

        String json = new ObjectMapper().writeValueAsString(client);

        Mockito.when(clientService.create(Mockito.any(Client.class))).thenReturn(clientSaved);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/api/clientes")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        mvc.perform(request).andExpect(status().isCreated())
                .andExpect(jsonPath("data.id").value(1))
                .andExpect(jsonPath("data.name").value(client.getName()));
    }

    @Test
    @WithMockUser
    void shouldUpdateClientes() throws Exception {
        Client client = Client.builder()
                .cpf("123456")
                .birthDate(new Date())
                .lastName("josé")
                .name("igor")
                .adresses(null)
                .build();

        Client clientUpdated = Client.builder()
                .id(1)
                .cpf("123456")
                .birthDate(new Date())
                .lastName("josé")
                .name("igor")
                .adresses(null)
                .build();

        String json = new ObjectMapper().writeValueAsString(client);

        Mockito.when(clientService.update(Mockito.anyLong(), Mockito.any(Client.class))).thenReturn(clientUpdated);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.put("/api/clientes/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        mvc.perform(request).andExpect(status().isOk())
                .andExpect(jsonPath("data.id").value(1))
                .andExpect(jsonPath("data.name").value(clientUpdated.getName()));
    }

}
