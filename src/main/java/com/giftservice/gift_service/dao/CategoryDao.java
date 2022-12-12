package com.giftservice.gift_service.dao;

import com.giftservice.gift_service.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryDao extends JpaRepository<Category, Long> {

}