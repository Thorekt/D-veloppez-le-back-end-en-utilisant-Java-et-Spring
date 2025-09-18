package com.thorekt.chatop.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.thorekt.chatop.model.DBUser;
import com.thorekt.chatop.repository.DBUserRepository;

/**
 * Custom implementation of UserDetailsService to load user details from the
 * database.
 * This service is used by Spring Security for authentication and authorization.
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private DBUserRepository dbUserRepository;

    /**
     * Load a user by username (in this case, email) from the database and return a
     * UserDetails object.
     * 
     * @param username the username (email) of the user to load
     * @return UserDetails object containing user information and authorities
     * @throws UsernameNotFoundException if the user is not found in the database
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Find the user by email (which is used as username because of
        // UserDetailsService implementation)
        DBUser user = dbUserRepository.findByEmail(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        // For simplicity, all users have the ROLE_USER authority here because we have
        // no roles in the DBUser entity
        List<SimpleGrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_USER"));
        return new User(user.getEmail(), user.getPassword(), authorities);
    }

}
