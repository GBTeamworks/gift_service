package com.giftservice.gift_service.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/gift")
public class GiftController {

    @GetMapping
    public String view(Model model) { return "gift/list"; }

    @PostMapping("/add")
    public String add(@PathVariable Long giftId) {
        return "gift/list";
    }

    @DeleteMapping("/delete/{giftId}")
    public String remove(@PathVariable Long giftId) {
        return "gift/list";
    }

    @PostMapping
    public String update() {
        return "gift/list";
    }


}
