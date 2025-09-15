
package com.thorekt.chatop.models;

import java.sql.Timestamp;

/**
 * User model representing a user in the system.
 * Includes fields for id, email, name, password, createdAt, and updatedAt.
 * Provides getters and setters for each field.
 */
public class User {
    private int id;
    private String email;
    private String name;
    private String password;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    /**
     * Constructor for User class.
     * Used when creating a new user with email, name, and password.
     * 
     * @param email    The email of the user.
     * @param name     The name of the user.
     * @param password The password of the user.
     */
    public User(String email, String name, String password) {
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }
}
