package com.giftservice.gift_service.dao;

import com.giftservice.gift_service.entities.Gift;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


public interface GiftDao extends JpaRepository<Gift, Long> {
//
//    @Override
//    // найти конкретный подарок по id
//    Optional<Gift> findById(Long id);
//    // список всех подарков
//    List<Gift> findAll();
//    // сохранить/обновить подарок
//    Gift saveAndFlush(Gift entity);
//    // удалить по id
//    void deleteById (Long id);

    // список всех подарков с фильтром по названию
    //    Optional<List<Gift>> findByTitleContaining(String title);




}
