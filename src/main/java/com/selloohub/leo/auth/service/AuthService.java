package com.selloohub.leo.auth.service;

import com.selloohub.leo.auth.dto.LoginRequest;
import com.selloohub.leo.auth.dto.LoginResponse;
import com.selloohub.leo.auth.security.AppUserDetails;
import com.selloohub.leo.common.security.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthService(AuthenticationManager authenticationManager, JwtService jwtService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    public LoginResponse login(LoginRequest request) {

        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.username(), request.password())
        );

        AppUserDetails userDetails = (AppUserDetails) auth.getPrincipal();
        String token = jwtService.generateToken(userDetails.getUsername(), userDetails.getRole());

        return new LoginResponse(token, userDetails.getRole(), userDetails.getDisplayName());
    }
}
