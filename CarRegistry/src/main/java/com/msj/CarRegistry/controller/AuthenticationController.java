package com.msj.CarRegistry.controller;

import com.msj.CarRegistry.controller.dtos.JwtResponse;
import com.msj.CarRegistry.controller.dtos.LogInRequest;
import com.msj.CarRegistry.controller.dtos.SignUpRequest;
import com.msj.CarRegistry.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping("/signup")
    public ResponseEntity<JwtResponse> signup(@RequestBody SignUpRequest request){
        try{
            return ResponseEntity.ok(authenticationService.signup(request));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody LogInRequest request){
        return ResponseEntity.ok(authenticationService.login(request));
    }
}
