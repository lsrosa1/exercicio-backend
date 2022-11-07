package com.example.desafio.client;

import com.example.desafio.model.dto.UserDTO;
import com.example.desafio.repository.UserRepository;
import com.example.desafio.security.JWTUtils;
import com.example.desafio.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JWTUtils jwtUtils;

    @Mock
    private PasswordEncoder passwordEncoder;

    private UserService userService;

    @BeforeEach
    void initUserService() {
        userService = new UserService(authenticationManager, userRepository, jwtUtils, passwordEncoder);
    }

    @Test
    @DisplayName("Deve fazer Login")
    public void shouldLogin() {

        UserDTO userDTO = UserDTO.builder().login("igor").password("1234").build();
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                userDTO.getLogin(), userDTO.getPassword());

        Mockito.when(jwtUtils.generateToken(Mockito.anyString())).thenReturn("token");

        String token = userService.login(userDTO);

        Assertions.assertEquals(token, "token");
        Mockito.verify(authenticationManager, Mockito.times(1)).authenticate(usernamePasswordAuthenticationToken);
    }

}
