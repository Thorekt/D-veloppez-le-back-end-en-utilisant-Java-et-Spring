package com.thorekt.chatop.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.thorekt.chatop.model.DBUser;

@Repository
public interface DBUserRepository extends CrudRepository<DBUser, Long> {

    DBUser findByEmail(String email);

    boolean existsByEmail(String email);
}
