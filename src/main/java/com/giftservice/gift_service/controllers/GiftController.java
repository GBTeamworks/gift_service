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
        model.addAttribute("gift", giftService.getGiftList());
        return "gift/list"; }

    @PostMapping("/add")
    public String add(@PathVariable String title) {
        giftService.createNewGift(title);
        return "gift/list";
    }

    @DeleteMapping("/delete/{giftId}")
    public String remove(@PathVariable Long giftId) {
        giftService.deleteById(giftId);
        return "gift/list";
    }

    @PostMapping
    public String update() {
        return "gift/list";
    }


}
