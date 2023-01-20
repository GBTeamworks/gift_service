package com.giftservice.gift_service.controllers;

import com.giftservice.gift_service.dto.GiftDto;
import com.giftservice.gift_service.entities.Cart;
import com.giftservice.gift_service.entities.Gift;
import com.giftservice.gift_service.entities.security.User;
import com.giftservice.gift_service.services.CartService;
import com.giftservice.gift_service.services.GiftService;
import com.giftservice.gift_service.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@AllArgsConstructor
@RequestMapping("/gifts")
public class GiftController {

    private final GiftService giftService;
    private final UserService userService;
    private final CartService cartService;

    @GetMapping
    public String view(Model model) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Optional<User> thisUser = userService.findByUsername(auth.getName());

        List<Gift> giftsFiltered = new ArrayList<>(giftService.getGiftList()
                .stream()
                .filter(p -> !p.getUser().getUsername().equals(thisUser.get().getUsername()))
                .filter(p -> p.getCart() == null)
                .toList());

        model.addAttribute("gifts", giftsFiltered);
        return "giftsPages/gifts";
    }

    @PostMapping("/will-give")
    public String willGive(@ModelAttribute("gift") GiftDto giftDto, String username) {

        Optional<User> wisher = userService.findByUsername(username);
        Gift gift = giftService.getGiftByUserAndTitle(wisher.get(), giftDto.getTitle());
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Optional<User> giver = userService.findByUsername(auth.getName());
        Cart cart;

        if (giver.get().getCart() == null) {
            cart = new Cart();
            cart.setGiver(giver.get());
        } else {
            cart = giver.get().getCart();
        }
        cart.getGifts().add(gift);
        cartService.save(cart);

        return "redirect:/lk/i-will-give";
    }
}