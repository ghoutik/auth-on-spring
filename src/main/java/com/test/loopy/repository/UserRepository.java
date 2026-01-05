package com.test.loopy.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.Optional;
import com.test.loopy.model.User;


public interface UserRepository extends MongoRepository<User, String>{
    Optional<User> findByUsername(String username);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
}
