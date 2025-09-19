package com.thorekt.chatop.dto.response;

import java.sql.Timestamp;

public record UserDataResponse(
        int id,
        String email,
        String name,
        Timestamp createdAt,
        Timestamp updatedAt) {
}
