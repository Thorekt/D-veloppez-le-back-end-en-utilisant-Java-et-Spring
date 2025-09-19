package com.thorekt.chatop.dto.response;

public record AuthResponse(String token, String error) {

    public AuthResponse(String token) {
        this(token, "n/a");
    }

}
