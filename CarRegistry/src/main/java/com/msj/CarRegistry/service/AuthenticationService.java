package com.msj.CarRegistry.service;

import com.msj.CarRegistry.controller.dtos.JwtResponse;
import com.msj.CarRegistry.controller.dtos.LogInRequest;
import com.msj.CarRegistry.controller.dtos.SignUpRequest;

public interface AuthenticationService {

    public JwtResponse signup(SignUpRequest request)throws Exception;

    public JwtResponse login(LogInRequest request);
}
