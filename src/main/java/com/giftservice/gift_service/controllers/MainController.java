package com.giftservice.gift_service.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/")
@Controller
public class MainController {

    @GetMapping
    public String index() {
        return "index";
    }

    @GetMapping("/users")
    public String usersPage() {
        return "users";
    }

    @GetMapping("/gifts")
    public String giftsPage() {
        return "gifts";
    }

    @GetMapping("/wishes")
    public String wishesPage() {
        return "wishes";
    }

    @GetMapping("/cart")
    public String cartPage() {
        return "cart";
    }

    @GetMapping("/login") //TODO Времянка, позже сделаю через Spring Security
    public String loginPage() {
        return "login";
    }

    @GetMapping("/registration") //TODO Времянка, позже сделаю через Spring Security
    public String registrationPage() {
        return "registration";
    }
}
