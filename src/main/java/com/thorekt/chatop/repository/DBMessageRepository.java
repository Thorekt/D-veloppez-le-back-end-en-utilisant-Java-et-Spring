package com.thorekt.chatop.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.thorekt.chatop.model.DBMessage;

/**
 * Repository for managing DBMessage entities
 * 
 * @author thorekt
 */
@Repository
public interface DBMessageRepository extends CrudRepository<DBMessage, Integer> {

}
