package com.thorekt.chatop.dto.response;

import java.sql.Timestamp;

import com.thorekt.chatop.model.DBUser;

/**
 * Response DTO for user data
 * 
 * @param id        User ID
 * @param email     User email
 * @param name      User name
 * @param createdAt Creation timestamp
 * @param updatedAt Last update timestamp
 * 
 * @author thorekt
 */
public record UserDataResponse(
                int id,
                String email,
                String name,
                Timestamp createdAt,
                Timestamp updatedAt) {

        /**
         * Convert DBUser to UserDataResponse
         * 
         * @param user
         * @return UserDataResponse DTO
         */
        public static UserDataResponse fromDBUser(DBUser user) {
                return new UserDataResponse(
                                user.getId(),
                                user.getEmail(),
                                user.getName(),
                                user.getCreatedAt(),
                                user.getUpdatedAt());
        }
}
