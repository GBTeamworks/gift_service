package com.giftservice.gift_service.controllers;

import com.giftservice.gift_service.dto.GiftDto;
import com.giftservice.gift_service.dto.UserDto;
import com.giftservice.gift_service.entities.Cart;
import com.giftservice.gift_service.entities.Gift;
import com.giftservice.gift_service.entities.security.User;
import com.giftservice.gift_service.security.JpaUserDetailService;
import com.giftservice.gift_service.services.CartService;
import com.giftservice.gift_service.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@Controller
@RequestMapping("/lk")
public class PersonalAccountController {

    private final JpaUserDetailService jpaUserDetailService;
    private final UserService userService;
    private final CartService cartService;


    public PersonalAccountController(JpaUserDetailService jpaUserDetailService, UserService userService, CartService cartService) {
        this.jpaUserDetailService = jpaUserDetailService;
        this.userService = userService;
        this.cartService = cartService;
    }

    @GetMapping
    public String view() {
        return "lkPages/personalAccount";
    }

    @GetMapping("/reg-data")
    public String registrationData(Model model) {
        UserDto userDto = new UserDto();

        User user = userService.findUserByUsername(jpaUserDetailService.getThisUsername());

        userDto.setUsername(user.getUsername());
        userDto.setBirthdate(user.getBirthdate().toString());
        userDto.setEmail(user.getEmail());
        model.addAttribute("user", userDto);
        return "lkPages/registrationDataPage";
    }

    @PostMapping("/reg-data")
    public String changeRegData(@ModelAttribute("user") UserDto userDto) {

        //TODO исправить сохранение данных. Пока работает только сохранение юзернейма.

        User existingUser = userService.findUserByEmail(userDto.getEmail());
        User user = userService.findUserByUsername(jpaUserDetailService.getThisUsername());

        if (existingUser != null && existingUser.getEmail() != null && !existingUser.getEmail().isEmpty()) {

            userDto.setPassword(existingUser.getPassword());
            userDto.setId(existingUser.getId());
            user.setUsername(userDto.getUsername());
            userService.updateUser(userDto);
            return "lkPages/personalAccount";
        }

        return "redirect:/lk/reg-data";
    }

    @GetMapping("/friends")
    public String friends(Model model) {

        User user = userService.findUserByUsername(jpaUserDetailService.getThisUsername());
        Set<User> friends = user.getSubscribers();

        model.addAttribute("friends", friends);

        return "lkPages/friendsPage";
    }

    @PostMapping("/delete-friend")
    public String deleteFriends(@ModelAttribute("user") UserDto friendUserDto) {

        User thisUser = userService.findUserByUsername(jpaUserDetailService.getThisUsername());
        UserDto thisUserDto = new UserDto();

        thisUserDto.setUsername(thisUser.getUsername());
        thisUserDto.setBirthdate(thisUser.getBirthdate().toString());
        thisUserDto.setEmail(thisUser.getEmail());
        userService.deleteFriend(thisUserDto, friendUserDto);
        return "redirect:/lk/friends";
    }

    @GetMapping("/gifts-list")
    public String giftsList(Model model) {
        return "redirect:/wishes";
    }

    @GetMapping("/i-will-give")
    public String iWillGive(Model model) {
        User thisUser = userService.findUserByUsername(jpaUserDetailService.getThisUsername());
        Cart cart = thisUser.getCart();

        model.addAttribute("gifts", cart.getGifts());
        return "lkPages/iWillGivePage";
    }

    @PostMapping("/i-will-give/add")
    public String iWillGiveAdd(@ModelAttribute("user") UserDto friendUserDto) {

        return "redirect:/lk/i-will-give";
    }

    @PostMapping("/delete-give")
    public String remove(@ModelAttribute("gift") GiftDto giftDto) {

        User thisUser = userService.findUserByUsername(jpaUserDetailService.getThisUsername());
        Gift giftToRemove = null;

        for (Gift giftInCart : thisUser.getCart().getGifts()) {
            if (giftInCart.getTitle().equals(giftDto.getTitle())
                    && giftInCart.getDescription().equals(giftDto.getDescription())) {

                giftToRemove = giftInCart;
            }
        }
        if (giftToRemove != null) {
            thisUser.getCart().getGifts().remove(giftToRemove);
            cartService.save(thisUser.getCart());
        }
        return "redirect:/lk/i-will-give";
    }
}