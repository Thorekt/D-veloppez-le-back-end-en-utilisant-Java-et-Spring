package com.thorekt.chatop.model;

import java.math.BigDecimal;
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
 * Entity representing a rental property
 * 
 * @author thorekt
 */
@Data
@Entity
@Table(name = "rentals")
public class DBRental {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private BigDecimal surface;
    private BigDecimal price;
    private String picture;
    private String description;
    private int ownerId;

    @CreationTimestamp
    @Column(name = "created_at")
    private Timestamp createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Timestamp updatedAt;

    /**
     * Default constructor for JPA
     */
    public DBRental() {
    }

    /**
     * Constructor for creating a new rental
     * 
     * @param name
     * @param description
     * @param picture
     * @param price
     * @param surface
     */
    public DBRental(String name, String description, String picture, BigDecimal price, BigDecimal surface,
            int ownerId) {
        this.name = name;
        this.description = description;
        this.picture = picture;
        this.price = price;
        this.surface = surface;
        this.ownerId = ownerId;
    }
}
