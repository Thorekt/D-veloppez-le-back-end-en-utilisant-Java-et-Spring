package com.thorekt.chatop.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.thorekt.chatop.models.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

}
