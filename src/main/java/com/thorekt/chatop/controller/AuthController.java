package com.thorekt.chatop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.thorekt.chatop.dto.request.LoginRequest;
import com.thorekt.chatop.dto.request.RegisterRequest;
import com.thorekt.chatop.dto.response.AuthResponse;
import com.thorekt.chatop.service.AuthenticationService;
import com.thorekt.chatop.service.RegistrationService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    public AuthenticationService authenticationService;

    @Autowired
    public RegistrationService registrationService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody @Valid LoginRequest request) {

        try {
            String token = authenticationService.authenticate(
                    request.email(),
                    request.password());

            return ResponseEntity.status(HttpStatus.CREATED).body(new AuthResponse(token));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new AuthResponse(null, e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new AuthResponse(null, e.getMessage()));
        }
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody @Valid RegisterRequest request) {
        try {
            registrationService.registerUser(request.email(), request.password(),
                    request.name());
            String token = authenticationService.authenticate(
                    request.email(),
                    request.password());

            return ResponseEntity.status(HttpStatus.CREATED).body(new AuthResponse(token));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new AuthResponse(null, e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new AuthResponse(null, e.getMessage()));
        }
    }

}
