package com.giftservice.gift_service.services;

import com.giftservice.gift_service.entities.Cart;
import com.giftservice.gift_service.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CartService {
    private final CartRepository cartRepository;

    public void save(Cart cart) {
        cartRepository.save(cart);
    }

    public List<Cart> findAll (){
       return cartRepository.findAll();
    }


}
