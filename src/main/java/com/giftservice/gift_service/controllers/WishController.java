package com.giftservice.gift_service.controllers;

import com.giftservice.gift_service.dto.GiftDto;
import com.giftservice.gift_service.entities.Gift;
import com.giftservice.gift_service.entities.security.User;
import com.giftservice.gift_service.security.JpaUserDetailService;
import com.giftservice.gift_service.services.GiftService;
import com.giftservice.gift_service.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/wishes")
public class WishController {

    private final JpaUserDetailService jpaUserDetailService;
    private final GiftService giftService;
    UserService userService;

    public WishController(JpaUserDetailService jpaUserDetailService, GiftService giftService, UserService userService) {
        this.jpaUserDetailService = jpaUserDetailService;
        this.giftService = giftService;
        this.userService = userService;
    }

    @GetMapping
    public String showWishesPage(Model model) {

        User thisUser = userService.findUserByUsername(jpaUserDetailService.getThisUsername());
        model.addAttribute("gifts", thisUser.getGifts());
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

        User thisUser = userService.findUserByUsername(jpaUserDetailService.getThisUsername());
        giftDto.setUser(thisUser);

        giftService.createNewGift(giftDto);

        return "redirect:/wishes";
    }

    @PostMapping("/delete-wish") //TODO Сделать
    public String deleteWish(@ModelAttribute("gift") GiftDto giftDto) {

        User thisUser = userService.findUserByUsername(jpaUserDetailService.getThisUsername());
        Gift wish = giftService.getGiftByUserAndTitle(thisUser, giftDto.getTitle());

        giftService.deleteById(wish.getId());

        return "redirect:/wishes";
    }
}
