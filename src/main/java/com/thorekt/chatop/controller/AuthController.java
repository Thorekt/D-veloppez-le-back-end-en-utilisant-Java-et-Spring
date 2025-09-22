package com.thorekt.chatop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RestController;

import com.thorekt.chatop.dto.request.LoginRequest;
import com.thorekt.chatop.dto.request.RegisterRequest;
import com.thorekt.chatop.dto.response.AuthResponse;
import com.thorekt.chatop.dto.response.UserDataResponse;
import com.thorekt.chatop.model.DBUser;
import com.thorekt.chatop.repository.DBUserRepository;
import com.thorekt.chatop.service.AuthenticationService;
import com.thorekt.chatop.service.RegistrationService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controller for authentication and registration
 * 
 * @author thorekt
 */
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private RegistrationService registrationService;

    @Autowired
    private DBUserRepository dbUserRepository;

    /**
     * Login a user
     * 
     * @param request
     * @return AuthResponse with JWT token if successful
     */
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

    /**
     * Register a new user
     * 
     * @param request
     * @return AuthResponse with JWT token if successful
     */
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody @Valid RegisterRequest request) {
        try {
            registrationService.registerUser(request.email(), request.password(),
                    request.name());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new AuthResponse(null, e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new AuthResponse(null, e.getMessage()));
        }

        return this.login(new LoginRequest(request.email(), request.password()));
    }

    /**
     * Get current user data
     * 
     * @param authentication
     * @return UserDataResponse of the current user
     */
    @GetMapping("/me")
    public ResponseEntity<UserDataResponse> me(Authentication authentication) {
        DBUser user = dbUserRepository.findByEmail(authentication.getName());
        return ResponseEntity.ok(new UserDataResponse(
                user.getId(),
                user.getEmail(),
                user.getName(),
                user.getCreatedAt(),
                user.getUpdatedAt()));
    }

}
