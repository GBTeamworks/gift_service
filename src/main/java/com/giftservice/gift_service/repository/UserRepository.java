package com.giftservice.gift_service.repository;

import com.giftservice.gift_service.entities.security.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);
    Optional<User> findById(Long id);
    Optional<User> findByUsername(String username);

}
