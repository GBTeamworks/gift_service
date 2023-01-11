package com.giftservice.gift_service.repository;

import com.giftservice.gift_service.entities.security.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorityRepository extends JpaRepository<Authority, Long> {

    Authority findByRole(String role);
}
