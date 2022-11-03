package com.example.desafio.service;

import com.example.desafio.exception.InvalidCredentialsException;
import com.example.desafio.model.dto.UsuarioDTO;
import com.example.desafio.repository.UsuarioRepository;
import com.example.desafio.security.JWTUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final AuthenticationManager authenticationManager;

    private final JWTUtils jwtUtils;
    private final PasswordEncoder passwordEncoder;

    public Map<String, Object> login(UsuarioDTO usuarioDTO) {

        try{
            System.out.println( passwordEncoder.encode(usuarioDTO.getSenha()));
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                    new UsernamePasswordAuthenticationToken(usuarioDTO.getLogin(), usuarioDTO.getSenha());
            authenticationManager.authenticate(usernamePasswordAuthenticationToken);

            String token = jwtUtils.generateToken(usuarioDTO.getLogin());
            return Collections.singletonMap("token", token);
        } catch (AuthenticationException ex) {
            throw new InvalidCredentialsException();
        }
    }
}
