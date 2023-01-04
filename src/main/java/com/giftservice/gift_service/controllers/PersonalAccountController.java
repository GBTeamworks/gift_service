package com.giftservice.gift_service.controllers;

import com.giftservice.gift_service.dto.UserDto;
import com.giftservice.gift_service.entities.security.User;
import com.giftservice.gift_service.security.JpaUserDetailService;
import com.giftservice.gift_service.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/lk")
public class PersonalAccountController {

    private JpaUserDetailService jpaUserDetailService;
    private UserService userService;
    private User user;

    public PersonalAccountController(JpaUserDetailService jpaUserDetailService, UserService userService) {
        this.jpaUserDetailService = jpaUserDetailService;
        this.userService = userService;
    }

    @GetMapping
    public String view() {
        return "lkPages/personalAccount";
    }

    @GetMapping("/reg-data")
    public String registrationData(Model model) {
        UserDto userDto = new UserDto();
        if (user == null) {
            user = userService.findUserByUsername(jpaUserDetailService.getThisUsername());
            userDto.setUsername(user.getUsername());
            userDto.setBirthdate(user.getBirthdate().toString());
            userDto.setEmail(user.getEmail());
            model.addAttribute("user", userDto);
            return "lkPages/registrationDataPage";
        } else {
            userDto.setUsername(user.getUsername());
            userDto.setBirthdate(user.getBirthdate().toString());
            userDto.setEmail(user.getEmail());
            model.addAttribute("user", userDto);
            return "lkPages/registrationDataPage";
        }
    }

    @PostMapping("/reg-data")
    public String changeRegData(@ModelAttribute("user") UserDto userDto,
                                Model model) {

        //TODO исправить сохранение данных. Пока работает только сохранение юзернейма.

        User existingUser = userService.findUserByEmail(userDto.getEmail());

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
    public String friends() {
        return "lkPages/friendsPage";
    } //todo доделать после создания страницы Пользователи

    @GetMapping("/gifts-list") //todo доделать после создания страниц Подарки
    public String giftsList() {
        return "lkPages/personalGiftListPage";
    }

    @GetMapping("/i-will-give") //todo доделать после создания страниц Подарки
    public String iWillGive() {
        return "lkPages/iWillGivePage";
    }
}