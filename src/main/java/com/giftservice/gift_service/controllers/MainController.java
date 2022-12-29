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
}
