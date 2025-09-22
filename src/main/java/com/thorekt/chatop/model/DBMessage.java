package com.thorekt.chatop.model;

import java.sql.Timestamp;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

/**
 * Entity representing a message in a rental conversation
 * 
 * @author thorekt
 */
@Data
@Entity
@Table(name = "messages")
public class DBMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int rentalId;
    private int userId;
    private String message;

    @CreationTimestamp
    @Column(name = "created_at")
    private Timestamp createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Timestamp updatedAt;

    /**
     * Default constructor for JPA
     */
    public DBMessage() {
    }

    /**
     * Constructor for creating a new message
     * 
     * @param userId
     * @param rentalId
     * @param message
     */
    public DBMessage(Integer userId, Integer rentalId, String message) {
        this.userId = userId;
        this.rentalId = rentalId;
        this.message = message;
    }

}
