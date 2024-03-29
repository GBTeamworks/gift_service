package com.giftservice.gift_service.services;

import com.giftservice.gift_service.dto.GiftDto;
import com.giftservice.gift_service.dto.UserDto;
import com.giftservice.gift_service.entities.Gift;
import com.giftservice.gift_service.entities.security.User;
import com.giftservice.gift_service.repository.GiftRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GiftService {

    private final GiftRepository giftRepository;

    public List<Gift> getGiftList() {
        return giftRepository.findAll();
    }

    public void createNewGift(GiftDto newGiftDto) {
        Gift newGift = new Gift();

        newGift.setTitle(newGiftDto.getTitle());
        newGift.setDescription(newGiftDto.getDescription());

        User user = new User();
        user.setId(newGiftDto.getUser().getId());
        user.setUsername(newGiftDto.getUser().getUsername());
        user.setEmail(newGiftDto.getUser().getEmail());
        newGift.setUser(user);

        giftRepository.save(newGift);
    }

    public Gift getGiftByUserAndTitle(User user, String title) {
        return giftRepository.findGiftByUserAndTitle(user, title);
    }

    public void deleteById(long id) {
        giftRepository.deleteById(id);
    }
}