package com.example.desafio.controller;

import com.example.desafio.exception.InvalidCredentialsException;
import com.example.desafio.model.dto.UserDTO;
import com.example.desafio.service.CustomUserDetailsService;
import com.example.desafio.service.UserService;
import com.google.gson.Gson;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.util.ArrayList;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    private final String API_URL = "/api/usuarios";
    @Autowired
    private MockMvc mvc;

    @Autowired
    private Gson gson;

    @MockBean
    private UserService userService;

    @MockBean
    private CustomUserDetailsService customUserDetailsService;


    @Test
    @DisplayName("Deve fazer login")
    public void shouldLogin() throws Exception {

        UserDTO userDTO = UserDTO.builder().login("igor").password("1234").build();
        UserDetails user = new User("igor", "1234", new ArrayList<>());

        String json = gson.toJson(userDTO);

        Mockito.when(userService.login(Mockito.any(UserDTO.class))).thenReturn("token");
        Mockito.when(customUserDetailsService.loadUserByUsername(userDTO.getLogin())).thenReturn(user);

        MockHttpServletRequestBuilder request = post(API_URL + "/login")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("data").isString());
    }

    @Test
    @DisplayName("Erro quando login não existir")
    public void errorWhenLoginNotFound() throws Exception {

        UserDTO userDTO = UserDTO.builder().login("igor").password("1234").build();
        String json = gson.toJson(userDTO);
        Mockito.when(userService.login(Mockito.any(UserDTO.class))).thenThrow(new InvalidCredentialsException());

        MockHttpServletRequestBuilder request = post(API_URL + "/login")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        mvc.perform(request)
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Deve criar um novo usuario")
    public void shouldCreateUser() throws Exception {

        com.example.desafio.entity.User userDTO = com.example.desafio.entity.User.builder().login("igor").password("1234").build();
        String json = gson.toJson(userDTO);

        Mockito.when(userService.register(Mockito.any(com.example.desafio.entity.User.class))).thenReturn("token");

        MockHttpServletRequestBuilder request = post(API_URL + "/registrar")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        mvc.perform(request)
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data").value("token"));
    }

}
