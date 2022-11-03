package com.example.desafio.controller;

import com.example.desafio.model.dto.UsuarioDTO;
import com.example.desafio.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UsuarioDTO userDTO) {

        Map<String, Object> token = usuarioService.login(userDTO);
        return ResponseEntity.ok().body(token);
    }
}
