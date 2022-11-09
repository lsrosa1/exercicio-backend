package com.example.desafio.service;

import com.example.desafio.exception.InvalidCredentialsException;
import com.example.desafio.exception.UsernameAlreadyUsedException;
import com.example.desafio.entity.User;
import com.example.desafio.repository.UserRepository;
import com.example.desafio.security.JWTUtils;
import com.example.desafio.model.dto.UserDTO;

import lombok.RequiredArgsConstructor;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final JWTUtils jwtUtils;
    private final PasswordEncoder passwordEncoder;

    public String login(UserDTO userDTO) {

        try {
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                    userDTO.getLogin(), userDTO.getPassword());
            authenticationManager.authenticate(usernamePasswordAuthenticationToken);
            return jwtUtils.generateToken(userDTO.getLogin());
        } catch (AuthenticationException ex) {
            throw new InvalidCredentialsException();
        }
    }

    public String register(User user) {
        Optional<User> optionalUser = userRepository.findByLogin(user.getLogin());

        if(optionalUser.isPresent()){
            throw new UsernameAlreadyUsedException();
        }

        String passwordEncoded = passwordEncoder.encode(user.getPassword());
        user.setPassword(passwordEncoded);
        
        userRepository.save(user);

        return jwtUtils.generateToken(user.getLogin());
    }

}
