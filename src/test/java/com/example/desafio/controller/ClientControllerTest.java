package com.example.desafio.controller;

import com.example.desafio.model.Client;
import com.example.desafio.service.ClientService;
import com.example.desafio.service.CustomUserDetailsService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.time.Month;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = ClientController.class)
@ActiveProfiles("test")
public class ClientControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private CustomUserDetailsService customUserDetailsService;

    @MockBean
    private ClientService clientService;

    @Test
    void shouldCreateClient() throws Exception {
        Client client = Client.builder()
                .cpf("123456")
                .birthDate(LocalDate.of(2000, Month.MAY, 12))
                .lastName("josé")
                .name("igor")
                .adresses(null)
                .build();

        Client clientSaved = Client.builder()
                .id(1)
                .cpf("123456")
                .birthDate(LocalDate.of(2000, Month.MAY, 12))
                .lastName("josé")
                .name("igor")
                .adresses(null)
                .build();

        Mockito.when(clientService.create(Mockito.any(Client.class))).thenReturn(clientSaved);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/api/clientes")
                .with(SecurityMockMvcRequestPostProcessors.jwt().authorities(new SimpleGrantedAuthority("USER")))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content("");

        mvc.perform(request).andExpect(status().isCreated())
                .andExpect(jsonPath("id").isNotEmpty());
    }

}
