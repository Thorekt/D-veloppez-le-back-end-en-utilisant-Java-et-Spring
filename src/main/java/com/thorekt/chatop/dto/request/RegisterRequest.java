package com.thorekt.chatop.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Request DTO for registration
 * 
 * @param email    User email
 * @param password User password
 * @param name     User name
 * 
 * @author thorekt
 */
public record RegisterRequest(
        @NotBlank @Email String email,
        @NotBlank @Size(min = 6, max = 64) String password,
        @NotBlank @Size(min = 1, max = 255) String name) {
}