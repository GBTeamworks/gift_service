package com.giftservice.gift_service.controllers;

import com.giftservice.gift_service.entities.Category;
import com.giftservice.gift_service.services.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/category")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public String view(Model model) {
        model.addAttribute("category", categoryService.findAll());
        return "category";
    }

    @PostMapping("/add")
    public String addCategory(Category category) {
        categoryService.createCategory(category);
        return "redirect:/category";
    }

    @DeleteMapping("/delete/{giftId}")
    public String remove(@PathVariable Long giftId) {
        categoryService.deleteById(giftId);
        return "redirect:/category";
    }

    @PostMapping("/update/{id}")
    public String update(@PathVariable Long id, Category category) {
        categoryService.updateCategory(id, category);
        return "redirect:/category";
    }
}