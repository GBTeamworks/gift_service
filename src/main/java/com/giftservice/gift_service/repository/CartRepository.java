package com.giftservice.gift_service.repository;

import com.giftservice.gift_service.entities.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {
}
