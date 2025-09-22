package com.thorekt.chatop.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.thorekt.chatop.dto.response.BaseResponse;
import com.thorekt.chatop.dto.response.RentalResponse;
import com.thorekt.chatop.dto.response.RentalsResponse;
import com.thorekt.chatop.model.DBRental;
import com.thorekt.chatop.service.RentalService;
import com.thorekt.chatop.service.UserService;

/**
 * Controller for rental management
 * 
 * @author thorekt
 */
@RestController
public class RentalController {

    @Autowired
    private RentalService rentalService;

    @Autowired
    private UserService userService;

    /**
     * Create a new rental
     * 
     * @param name
     * @param description
     * @param price
     * @param surface
     * @param picture
     * @return BaseResponse indicating success or failure
     */
    @PostMapping(value = "/rentals", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BaseResponse> createRental(
            Authentication authentication,
            @RequestParam("name") String name,
            @RequestParam("description") String description,
            @RequestParam("price") BigDecimal price,
            @RequestParam("surface") BigDecimal surface,
            @RequestParam("picture") MultipartFile picture) {

        try {
            int ownerId = userService.getUserByEmail(authentication.getName()).getId();
            rentalService.createRental(name, description, price, surface, picture, ownerId);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new BaseResponse(e.getMessage()));
        }

        return ResponseEntity.ok(new BaseResponse("Rental created !"));
    }

    /**
     * Update rental by id
     * 
     * @param id
     * @param name
     * @param description
     * @param price
     * @param surface
     * @param picture
     * @return BaseResponse indicating success or failure
     */
    @PutMapping("/rentals/{id}")
    public ResponseEntity<BaseResponse> updateRental(
            @PathVariable int id,
            @RequestPart("name") String name,
            @RequestPart("description") String description,
            @RequestPart("price") BigDecimal price,
            @RequestPart("surface") BigDecimal surface,
            @RequestPart("picture") MultipartFile picture) {

        try {
            rentalService.updateRental(id, name, description, price, surface, picture);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new BaseResponse(e.getMessage()));
        }

        return ResponseEntity.ok(new BaseResponse("Rental updated !"));
    }

    /**
     * Get rental by id
     * 
     * @param id
     * @return RentalResponse or 400 if not found
     */
    @GetMapping("/rentals/{id}")
    public ResponseEntity<RentalResponse> getRentalById(int id) {

        try {
            DBRental rentalEntity = rentalService.getRentalById(id);
            RentalResponse rental = RentalResponse.fromDBRental(rentalEntity);
            return ResponseEntity.ok(rental);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }

    }

    /**
     * Get all rentals
     * 
     * @return RentalsResponse with list of rentals
     */
    @GetMapping("/rentals")
    public ResponseEntity<RentalsResponse> getAllRentals() {

        Iterable<DBRental> rentalEntities = rentalService.getAllRentals();

        RentalsResponse rentalsResponse = RentalsResponse.fromRentalResponses(rentalEntities);

        return ResponseEntity.ok(rentalsResponse);
    }
}
