package com.thorekt.chatop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thorekt.chatop.dto.response.BaseResponse;
import com.thorekt.chatop.model.DBMessage;
import com.thorekt.chatop.repository.DBMessageRepository;
import com.thorekt.chatop.repository.DBRentalRepository;
import com.thorekt.chatop.repository.DBUserRepository;

/**
 * Service for message management
 * 
 * @author thorekt
 */
@Service
public class MessageService {

    @Autowired
    private DBMessageRepository dbMessageRepository;

    @Autowired
    private DBRentalRepository dbRentalRepository;

    @Autowired
    private DBUserRepository dbUserRepository;

    /**
     * Create a new message
     * 
     * @param message
     * @param userId
     * @param rentalId
     * @throws Exception
     */
    @Transactional
    public void createMessage(String message, int userId, int rentalId) throws Exception {
        boolean rentalExist = dbRentalRepository.existsById(rentalId);

        if (!rentalExist) {
            throw new IllegalArgumentException("Rental does not exist");
        }

        boolean userExist = dbUserRepository.existsById(userId);

        if (!userExist) {
            throw new IllegalArgumentException("User does not exist");
        }

        if (message == null || message.isEmpty()) {
            throw new IllegalArgumentException("Message cannot be empty");
        }

        dbMessageRepository.save(new DBMessage(userId, rentalId, message));
    }
}
