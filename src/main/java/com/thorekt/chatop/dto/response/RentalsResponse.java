package com.thorekt.chatop.dto.response;

import java.util.ArrayList;
import java.util.List;

import com.thorekt.chatop.model.DBRental;

/**
 * Response DTO for multiple rentals
 * 
 * @param rentals List of RentalResponse
 * 
 * @author thorekt
 */
public record RentalsResponse(
                List<RentalResponse> rentals) {

        /**
         * Convert iterable of DBRental to RentalsResponse
         * 
         * @param rentalEntities
         * @return RentalsResponse DTO
         */
        public static RentalsResponse fromRentalResponses(Iterable<DBRental> rentalEntities) {
                List<RentalResponse> rentalResponses = new ArrayList<>();
                for (DBRental rentalEntity : rentalEntities) {
                        RentalResponse rentalResponse = RentalResponse.fromDBRental(rentalEntity);
                        rentalResponses.add(rentalResponse);
                }
                return new RentalsResponse(rentalResponses);
        }
}
