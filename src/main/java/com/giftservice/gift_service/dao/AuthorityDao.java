package com.giftservice.gift_service.dao;

import com.giftservice.gift_service.entities.security.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorityDao extends JpaRepository<Authority, Long> {
}
