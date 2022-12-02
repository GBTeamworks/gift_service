package com.giftservice.gift_service.dao;

import com.giftservice.gift_service.entities.security.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserDao extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);
}
