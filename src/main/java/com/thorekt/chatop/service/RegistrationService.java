package com.thorekt.chatop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thorekt.chatop.model.DBUser;
import com.thorekt.chatop.repository.DBUserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RegistrationService {

    @Autowired
    private DBUserRepository dbUserRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public void registerUser(String email, String rawPassword, String name) throws Exception {
        if (dbUserRepository.findByEmail(email) != null) {
            throw new IllegalArgumentException("Email already in use");
        }

        DBUser newUser = new DBUser(email, name, passwordEncoder.encode(rawPassword));
        dbUserRepository.save(newUser);
    }

}
