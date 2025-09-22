package com.thorekt.chatop.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

/**
 * Request DTO for login
 * 
 * @param email    User email
 * @param password User password
 * 
 * @author thorekt
 */
public record LoginRequest(
        @NotBlank @Email String email,
        @NotBlank String password) {

}
