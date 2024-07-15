package com.msj.CarRegistry.controller;

import com.msj.CarRegistry.controller.dtos.JwtResponse;
import com.msj.CarRegistry.controller.dtos.LogInRequest;
import com.msj.CarRegistry.controller.dtos.SignUpRequest;
import com.msj.CarRegistry.service.AuthenticationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AuthenticationControllerTest {

    @InjectMocks
    private AuthenticationController authenticationController;

    @Mock
    private AuthenticationService authenticationService;

    private SignUpRequest signUpRequest;
    private LogInRequest logInRequest;
    private JwtResponse jwtResponse;

    @BeforeEach
    void setUp() {
        signUpRequest = SignUpRequest.builder()
                .name("user")
                .mail("user@mail.com")
                .password("password")
                .build();

        logInRequest = LogInRequest.builder()
                .mail("user@mail.com")
                .password("password")
                .build();

        jwtResponse = JwtResponse.builder()
                .token("token")
                .build();
    }

    @Test
    void testSignup() throws Exception {
        when(authenticationService.signup(any(SignUpRequest.class))).thenReturn(jwtResponse);

        ResponseEntity<JwtResponse> response = authenticationController.signup(signUpRequest);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(jwtResponse.getToken(), response.getBody().getToken());
    }

    @Test
    void testLogin() {
        when(authenticationService.login(any(LogInRequest.class))).thenReturn(jwtResponse);

        ResponseEntity<JwtResponse> response = authenticationController.login(logInRequest);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(jwtResponse.getToken(), response.getBody().getToken());
    }

    @Test
    void testSignupException() throws Exception {
        when(authenticationService.signup(any(SignUpRequest.class))).thenThrow(new RuntimeException());

        ResponseEntity<JwtResponse> response = authenticationController.signup(signUpRequest);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
}
