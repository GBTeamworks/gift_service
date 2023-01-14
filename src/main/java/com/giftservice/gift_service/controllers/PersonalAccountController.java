package com.giftservice.gift_service.controllers;

import com.giftservice.gift_service.dto.GiftDto;
import com.giftservice.gift_service.dto.UserDto;
import com.giftservice.gift_service.entities.Cart;
import com.giftservice.gift_service.entities.Gift;
import com.giftservice.gift_service.entities.security.User;
import com.giftservice.gift_service.repository.UserRepository;
import com.giftservice.gift_service.services.CartService;
import com.giftservice.gift_service.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;

@Controller
@RequiredArgsConstructor
@RequestMapping("/lk")
public class PersonalAccountController {

    private final UserService userService;
    private final CartService cartService;
    private final UserRepository userRepository;
    private Long thisUserId;
    private String thisUserUsername;

    @GetMapping
    public String view() {
        return "lkPages/personalAccount";
    }

    @GetMapping("/user-info")
    public String showForm(Model model) {

        UserDto userDto = new UserDto();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Optional<User> user = null;

        try {
            if (thisUserId != null && thisUserId.equals(userService.findByUsername(auth.getName()).get().getId())) {
                user = userRepository.findById(thisUserId);
            } else {
                user = userService.findByUsername(auth.getName());
                thisUserId = user.get().getId();
            }
        } catch (NoSuchElementException e) {
            user = userService.findByUsername(thisUserUsername);
        }

        userDto.setUsername(user.get().getUsername());
        userDto.setBirthdate(user.get().getBirthdate().toString());
        userDto.setEmail(user.get().getEmail());
        model.addAttribute("user", userDto);
        return "lkPages/userInfoPage";
    }

    @PostMapping("/user-info")
    public String changeUserInfo(@ModelAttribute("user") UserDto userDto) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Optional<User> user;


        try {
            if (thisUserId != null && thisUserId.equals(userService.findByUsername(auth.getName()).get().getId())) {
                user = userRepository.findById(thisUserId);
            } else {
                user = userService.findByUsername(auth.getName());
                thisUserId = user.get().getId();
            }
        } catch (NoSuchElementException e) {
            user = userService.findByUsername(thisUserUsername);
        }

        userDto.setId(user.get().getId());
        user.get().setUsername(userDto.getUsername());
        user.get().setEmail(userDto.getEmail());
        user.get().setBirthdate(LocalDate.parse(userDto.getBirthdate()));
        thisUserUsername = userDto.getUsername();
        userService.update(userDto);
        return "lkPages/personalAccount";
    }

    @GetMapping("/friends")
    public String friends(Model model) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Optional<User> user = userService.findByUsername(auth.getName());
        Set<User> friends = user.get().getSubscribers();

        model.addAttribute("friends", friends);

        return "lkPages/friendsPage";
    }

    @PostMapping("/delete-friend")
    public String deleteFriends(@ModelAttribute("user") UserDto friendUserDto) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Optional<User> thisUser = userService.findByUsername(auth.getName());
        UserDto thisUserDto = new UserDto();

        thisUserDto.setUsername(thisUser.get().getUsername());
        thisUserDto.setBirthdate(thisUser.get().getBirthdate().toString());
        thisUserDto.setEmail(thisUser.get().getEmail());
        userService.deleteFriend(thisUserDto, friendUserDto);
        return "redirect:/lk/friends";
    }

    @GetMapping("/gifts-list")
    public String giftsList(Model model) {
        return "redirect:/wishes";
    }

    @GetMapping("/i-will-give")
    public String iWillGive(Model model) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Optional<User> thisUser = userService.findByUsername(auth.getName());
        Cart cart;
        if (thisUser.get().getCart() == null) {
            cart = new Cart();
            cart.setGiver(thisUser.get());
        } else {
            cart = thisUser.get().getCart();
        }

        model.addAttribute("gifts", cart.getGifts());
        return "lkPages/iWillGivePage";
    }

    @PostMapping("/i-will-give/add")
    public String iWillGiveAdd(@ModelAttribute("user") UserDto friendUserDto) {

        return "redirect:/lk/i-will-give";
    }

    @PostMapping("/delete-give")
    public String remove(@ModelAttribute("gift") GiftDto giftDto) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Optional<User> thisUser = userService.findByUsername(auth.getName());
        Gift giftToRemove = null;

        for (Gift giftInCart : thisUser.get().getCart().getGifts()) {
            if (giftInCart.getTitle().equals(giftDto.getTitle())
                    && giftInCart.getDescription().equals(giftDto.getDescription())) {

                giftToRemove = giftInCart;
            }
        }
        if (giftToRemove != null) {
            thisUser.get().getCart().getGifts().remove(giftToRemove);
            cartService.save(thisUser.get().getCart());
        }
        return "redirect:/lk/i-will-give";
    }
}