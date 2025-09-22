package com.thorekt.chatop.dto.request;

/**
 * Request DTO for posting a message
 * 
 * @param message  Message content
 * @param userId   ID of the user sending the message
 * @param rentalId ID of the rental the message is associated with
 * 
 * @author thorekt
 */
public record MessageRequest(
                String message,
                int user_id,
                int rental_id) {

}
