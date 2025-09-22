package com.thorekt.chatop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thorekt.chatop.model.DBUser;
import com.thorekt.chatop.repository.DBUserRepository;

/**
 * Service for user management
 * 
 * @author thorekt
 */
@Service
public class UserService {

    @Autowired
    private DBUserRepository dbUserRepository;

    /**
     * Get user by id
     * 
     * @param id the id of the user
     * @return the user
     */
    @Transactional(readOnly = true)
    public DBUser getUserById(int id) {
        if (!dbUserRepository.existsById(id)) {
            throw new IllegalArgumentException("User with id " + id + " does not exist");
        }
        return dbUserRepository.findById(id);
    }

    /**
     * Get user by email
     * 
     * @param email the email of the user
     * @return the user
     */
    @Transactional(readOnly = true)
    public DBUser getUserByEmail(String email) {
        if (!dbUserRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("User with email " + email + " does not exist");
        }
        return dbUserRepository.findByEmail(email);
    }

}
