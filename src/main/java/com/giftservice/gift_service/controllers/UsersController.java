package com.giftservice.gift_service.controllers;

import com.giftservice.gift_service.dto.UserDto;
import com.giftservice.gift_service.entities.security.User;
import com.giftservice.gift_service.repository.UserRepository;
import com.giftservice.gift_service.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Controller
@AllArgsConstructor
@RequestMapping("/users")
public class UsersController {

    private final UserRepository userRepository;
    private final UserService userService;
    private List<User> users;

    @GetMapping
    public String showUsersPage(Model model) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Optional<User> thisUser = userService.findByUsername(auth.getName());

        users = userRepository.findAll();
        List<User> userListToShow;
        Set<User> friends = thisUser.get().getSubscribers();

        users.removeAll(friends);
        userListToShow = users.stream()
                .filter(p -> !p.getEmail().equals("testUser@mail.ru"))
                .filter(p -> !p.getEmail().equals("testAdmin@mail.ru"))
                .filter(p -> !p.getEmail().equals(thisUser.get().getEmail()))
                .toList();

        model.addAttribute("users", userListToShow);

        return "basePages/users";
    }

    @PostMapping("/add-friend")
    public String addFriend(@ModelAttribute("user") UserDto friendUserDto) {

        UserDto thisUserDto = new UserDto();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (friendUserDto != null && friendUserDto.getEmail() != null && !friendUserDto.getEmail().isEmpty()) {

            Optional<User> thisUser = userService.findByUsername(auth.getName());

            thisUserDto.setUsername(thisUser.get().getUsername());
            thisUserDto.setBirthdate(thisUser.get().getBirthdate().toString());
            thisUserDto.setEmail(thisUser.get().getEmail());
            userService.addFriend(thisUserDto, friendUserDto);

            return "redirect:/lk/friends";
        }
        return "redirect:/users";
    }
}