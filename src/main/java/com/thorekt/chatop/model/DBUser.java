
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
 * User model representing a user in the system.
 * Includes fields for id, email, name, password, createdAt, and updatedAt.
 * Provides getters and setters for each field.
 * 
 * @author thorekt
 */
@Data
@Entity
@Table(name = "users")
public class DBUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String email;
    private String name;
    private String password;

    @CreationTimestamp
    @Column(name = "created_at")
    private Timestamp createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Timestamp updatedAt;

    public DBUser() {
    }

    /**
     * Constructor for User class.
     * Used when creating a new user with email, name, and password.
     * 
     * @param email    The email of the user.
     * @param name     The name of the user.
     * @param password The password of the user.
     */
    public DBUser(String email, String name, String password) {
        this.email = email;
        this.name = name;
        this.password = password;
    }

    /**
     * Constructor for User class.
     * Used when logging in a user with email and password.
     *
     * @param email    The email of the user.
     * @param password The password of the user.
     */
    public DBUser(String email, String password) {
        this.email = email;
        this.password = password;
    }

}
