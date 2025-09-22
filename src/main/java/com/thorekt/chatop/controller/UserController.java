package com.thorekt.chatop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.thorekt.chatop.dto.response.UserDataResponse;
import com.thorekt.chatop.model.DBUser;
import com.thorekt.chatop.service.UserService;

/**
 * Controller for user management
 * 
 * @author thorekt
 */
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * Get user by id
     * 
     * @param id
     * @return UserDataResponse of the user
     */
    @GetMapping("/user/{id}")
    public ResponseEntity<UserDataResponse> getUser(@PathVariable int id) {

        try {
            DBUser user = userService.getUserById(id);

            UserDataResponse response = UserDataResponse.fromDBUser(user);

            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }

    }

}
