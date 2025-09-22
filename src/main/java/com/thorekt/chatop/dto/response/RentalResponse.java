package com.thorekt.chatop.dto.response;

import java.math.BigDecimal;
import java.sql.Timestamp;

import com.thorekt.chatop.model.DBRental;

/**
 * Response DTO for rental data
 * 
 * @param id          Rental ID
 * @param name        Rental name
 * @param description Rental description
 * @param picture     Rental picture path
 * @param price       Rental price
 * @param surface     Rental surface
 * @param ownerId     Owner user ID
 * @param createdAt   Creation timestamp
 * @param updatedAt   Last update timestamp
 * 
 * @author thorekt
 */
public record RentalResponse(
                int id,
                String name,
                String description,
                String picture,
                BigDecimal price,
                BigDecimal surface,
                int ownerId,
                Timestamp createdAt,
                Timestamp updatedAt) {

        /**
         * Convert DBRental to RentalResponse
         * 
         * @param dbRental DBRental entity
         * @return RentalResponse DTO
         */
        public static RentalResponse fromDBRental(DBRental dbRental) {
                return new RentalResponse(
                                dbRental.getId(),
                                dbRental.getName(),
                                dbRental.getDescription(),
                                dbRental.getPicture(),
                                dbRental.getPrice(),
                                dbRental.getSurface(),
                                dbRental.getOwnerId(),
                                dbRental.getCreatedAt(),
                                dbRental.getUpdatedAt());
        }

}
