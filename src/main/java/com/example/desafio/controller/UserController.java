package com.example.desafio.controller;

import com.example.desafio.model.User;
import com.example.desafio.service.UserService;
import com.example.desafio.model.dto.UserDTO;
import com.example.desafio.utils.ApiResponse;

import lombok.RequiredArgsConstructor;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse> login(@RequestBody UserDTO userDTO) {

        String token = userService.login(userDTO);

        return ResponseEntity
                .ok(ApiResponse.builder().status(HttpStatus.OK.value()).menssage("Login realizado com sucesso")
                        .data(token).build());
    }

    @PostMapping("/registrar")
    public ResponseEntity<ApiResponse> register(@RequestBody @Valid User user) {

        String token = userService.register(user);

        return ResponseEntity
                .ok(ApiResponse.builder().status(HttpStatus.OK.value()).menssage("Usuario criado com sucesso")
                        .data(token).build());
    }
}
