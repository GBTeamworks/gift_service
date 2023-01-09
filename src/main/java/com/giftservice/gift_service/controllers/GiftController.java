package com.giftservice.gift_service.controllers;

import com.giftservice.gift_service.dto.GiftDto;
import com.giftservice.gift_service.entities.Cart;
import com.giftservice.gift_service.entities.Gift;
import com.giftservice.gift_service.entities.security.User;
import com.giftservice.gift_service.security.JpaUserDetailService;
import com.giftservice.gift_service.services.CartService;
import com.giftservice.gift_service.services.GiftService;
import com.giftservice.gift_service.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/gifts")
public class GiftController {

    private final GiftService giftService;
    private final JpaUserDetailService jpaUserDetailService;
    private final UserService userService;
    private final CartService cartService;

    public GiftController(GiftService giftService, JpaUserDetailService jpaUserDetailService, UserService userService, CartService cartService) {
        this.giftService = giftService;
        this.jpaUserDetailService = jpaUserDetailService;
        this.userService = userService;
        this.cartService = cartService;
    }

    @GetMapping
    public String view(Model model) {
        User thisUser = userService.findUserByUsername(jpaUserDetailService.getThisUsername());
        String username = "";
        List<Gift> giftsFiltered = new java.util.ArrayList<>(giftService.getGiftList()
                .stream()
                .filter(p -> !p.getUser().getUsername().equals(thisUser.getUsername()))
                .filter(p -> p.getCart() == null)
                .toList());

        model.addAttribute("gifts", giftsFiltered);
        model.addAttribute("username", username);
        return "giftsPages/gifts";
    }

    @PostMapping("/will-give")
    public String willGive(@ModelAttribute("gift") GiftDto giftDto, @ModelAttribute("username") String username) {

        User wisher = userService.findUserByUsername(username);
        Gift gift = giftService.getGiftByUserAndTitle(wisher, giftDto.getTitle());
        User giver = userService.findUserByUsername(jpaUserDetailService.getThisUsername());
        Cart cart;

        if (giver.getCart() == null) {
            cart = new Cart();
            cart.setGiver(giver);
        } else {
            cart = giver.getCart();
        }
        cart.getGifts().add(gift);
        cartService.save(cart);

        return "redirect:/lk/i-will-give";
    }
}