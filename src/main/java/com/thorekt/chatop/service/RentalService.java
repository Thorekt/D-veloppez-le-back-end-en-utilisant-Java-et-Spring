package com.thorekt.chatop.service;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.thorekt.chatop.model.DBRental;
import com.thorekt.chatop.repository.DBRentalRepository;

/**
 * Service for rental management
 * 
 * @author thorekt
 */
@Service
public class RentalService {

    @Autowired
    private DBRentalRepository rentalRepository;

    /**
     * Create a new rental
     * 
     * @param name
     * @param description
     * @param price
     * @param surface
     * @param picture
     * @throws Exception
     */
    @Transactional
    public void createRental(String name,
            String description,
            BigDecimal price,
            BigDecimal surface,
            MultipartFile picture) throws Exception {

        String picturePath = savePicture(picture);

        DBRental rental = new DBRental(
                name,
                description,
                picturePath,
                price,
                surface);

        rentalRepository.save(rental);
    }

    /**
     * Update an existing rental
     * 
     * @param id
     * @param name
     * @param description
     * @param price
     * @param surface
     * @param picture
     * @throws Exception
     */
    @Transactional
    public void updateRental(int id,
            String name,
            String description,
            BigDecimal price,
            BigDecimal surface,
            MultipartFile picture) throws Exception {

        if (!rentalRepository.existsById(id)) {
            throw new IllegalArgumentException("Rental with id " + id + " does not exist");
        }

        String picturePath = savePicture(picture);

        DBRental rental = rentalRepository.findById(id);
        rental.setName(name);
        rental.setDescription(description);
        rental.setPrice(price);
        rental.setSurface(surface);
        rental.setPicture(picturePath);

        rentalRepository.save(rental);
    }

    /**
     * Save picture and return its path
     * 
     * @param picture
     * @return picture path
     * @throws Exception
     */
    private String savePicture(MultipartFile picture) throws Exception {
        // Logic to save the picture and return its URL or path
        return "path/to/saved/picture.jpg";
    }

    /**
     * Get rental by id
     * 
     * @param id
     * @return DBRental
     * @throws Exception
     */
    @Transactional(readOnly = true)
    public DBRental getRentalById(int id) throws Exception {
        if (!rentalRepository.existsById(id)) {
            throw new IllegalArgumentException("Rental with id " + id + " does not exist");
        }
        return rentalRepository.findById(id);
    }

    /**
     * Get all rentals
     * 
     * @return Iterable of DBRental
     */
    @Transactional(readOnly = true)
    public Iterable<DBRental> getAllRentals() {
        return rentalRepository.findAll();
    }
}
