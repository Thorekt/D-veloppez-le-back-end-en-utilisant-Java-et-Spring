package com.thorekt.chatop.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.thorekt.chatop.model.DBRental;

/**
 * Repository for managing DBRental entities
 * 
 * @author thorekt
 */
@Repository
public interface DBRentalRepository extends CrudRepository<DBRental, Integer> {
    boolean existsById(int id);

    DBRental findById(int id);

    Iterable<DBRental> findAll();
}
