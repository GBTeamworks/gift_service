package com.giftservice.gift_service.controllers;

import com.giftservice.gift_service.dto.GiftDto;
import com.giftservice.gift_service.dto.UserDto;
import com.giftservice.gift_service.entities.Gift;
import com.giftservice.gift_service.entities.security.User;
import com.giftservice.gift_service.services.GiftService;
import com.giftservice.gift_service.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@AllArgsConstructor
@RequestMapping("/wishes")
public class WishController {

    private final GiftService giftService;
    private final UserService userService;

    @GetMapping
    public String showWishesPage(Model model) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Optional<User> thisUser = userService.findByUsername(auth.getName());
        model.addAttribute("gifts", thisUser.get().getGifts());
        return "wishesPages/wishes";
    }

    @GetMapping("/add-wish")
    public String addNewWish(Model model) {

        GiftDto gift = new GiftDto();
        model.addAttribute("gift", gift);

        return "wishesPages/addNewWish";
    }

    @PostMapping("/add-wish")
    public String addNewWish(@ModelAttribute("wish") GiftDto giftDto) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Optional<User> thisUser = userService.findByUsername(auth.getName());
        UserDto userDto = new UserDto();
        userDto.setId(thisUser.get().getId());
        userDto.setUsername(thisUser.get().getUsername());
        userDto.setEmail(thisUser.get().getEmail());

        giftDto.setUser(userDto);

        giftService.createNewGift(giftDto);

        return "redirect:/wishes";
    }

    @PostMapping("/delete-wish")
    public String deleteWish(@ModelAttribute("gift") GiftDto giftDto) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Optional<User> thisUser = userService.findByUsername(auth.getName());
        Gift wish = giftService.getGiftByUserAndTitle(thisUser.get(), giftDto.getTitle());

        giftService.deleteById(wish.getId());

        return "redirect:/wishes";
    }
}
