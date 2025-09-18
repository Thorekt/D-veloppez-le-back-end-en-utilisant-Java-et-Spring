package com.thorekt.chatop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

@Service
public class AuthenticationService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JWTService jwtService;

    /**
     * Authenticate a user and generate a JWT token upon successful authentication.
     * 
     * @param email
     * @param password
     * @return
     * @throws Exception
     */
    public String authenticate(String email, String password) throws Exception {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password));
        String token = jwtService.generateToken(auth);
        return token;
    }
}
