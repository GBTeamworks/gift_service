package com.giftservice.gift_service.controllers;

import com.giftservice.gift_service.dao.GiftDao;
import com.giftservice.gift_service.services.GiftService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/gift")
@RequiredArgsConstructor
public class GiftController {

    private final GiftService giftService;

    @GetMapping
    public String view(Model model) {
        model.addAttribute("gift", giftService.findAll());
        return "gift/list"; }

    @PutMapping("/")
    public String add(@PathVariable String title) {
        giftService.create(title);
        return "gift/list";
    }

    @PutMapping("/delete/{giftId}")
    public String remove(@PathVariable Long giftId) {
        giftService.deleteById(giftId);
        return "gift/list";
    }

    @PostMapping
    public String update() {
        return "gift/list";
    }


}
