package com.giftservice.gift_service.repository;

import com.giftservice.gift_service.entities.Gift;
import com.giftservice.gift_service.entities.security.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GiftRepository extends JpaRepository<Gift, Long> {

    Gift findGiftByUserAndTitle(User user, String title);
}
