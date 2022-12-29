package com.giftservice.gift_service.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/cart")
public class CartController {

    @GetMapping
    public String view(Model model) {
        return "cart";
    }

    @PutMapping("/{giftId}")
    public String add(@PathVariable Long giftId) {
        return "redirect:/gifts";
    }

    @PutMapping("/delete/{giftId}")
    public String remove(@PathVariable Long giftId) {
        return "redirect:/cart";
    }

    @PostMapping
    public String update() {
        return "redirect:/cart";
    }


}
