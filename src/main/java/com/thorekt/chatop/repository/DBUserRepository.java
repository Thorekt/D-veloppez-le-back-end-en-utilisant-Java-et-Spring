package com.thorekt.chatop.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.thorekt.chatop.model.DBUser;

/**
 * Repository for managing DBUser entities
 * 
 * @author thorekt
 */
@Repository
public interface DBUserRepository extends CrudRepository<DBUser, Integer> {

    DBUser findByEmail(String email);

    boolean existsByEmail(String email);

    DBUser findById(int id);

    boolean existsById(int id);
}
