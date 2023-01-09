package com.giftservice.gift_service.controllers;

import com.giftservice.gift_service.dto.UserDto;
import com.giftservice.gift_service.entities.security.User;
import com.giftservice.gift_service.repository.UserRepository;
import com.giftservice.gift_service.security.JpaUserDetailService;
import com.giftservice.gift_service.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/users")
public class UsersController {

    UserRepository userRepository;
    UserService userService;
    List<User> users;
    private final JpaUserDetailService jpaUserDetailService;

    public UsersController(UserRepository userRepository,
                           UserService userService,
                           List<User> users,
                           JpaUserDetailService jpaUserDetailService) {
        this.userRepository = userRepository;
        this.userService = userService;
        this.users = users;
        this.jpaUserDetailService = jpaUserDetailService;
    }

    @GetMapping
    public String showUsersPage(Model model) {

        User thisUser = userService.findUserByUsername(jpaUserDetailService.getThisUsername());

        users = userRepository.findAll();
        List<User> userListToShow = new ArrayList<>();
        Set<User> friends = thisUser.getSubscribers();

        int counter;
        for (User user : users) {
            counter = 0;
            for (User friend : friends) {

                if (!user.getEmail().equals(friend.getEmail())
                        && !user.getEmail().equals("testUser@mail.ru")
                        && !user.getEmail().equals("testAdmin@mail.ru")
                        && !user.getEmail().equals(thisUser.getEmail())) {

                    counter++;
                }
            }
            if (counter == friends.size()) {

                userListToShow.add(user);
            }
        }
        model.addAttribute("users", userListToShow);

        return "users";
    }

    @PostMapping("/add-friend")
    public String addFriend(@ModelAttribute("user") UserDto friendUserDto) {

        UserDto thisUserDto = new UserDto();

        if (friendUserDto != null && friendUserDto.getEmail() != null && !friendUserDto.getEmail().isEmpty()) {

            User thisUser = userService.findUserByUsername(jpaUserDetailService.getThisUsername());

            thisUserDto.setUsername(thisUser.getUsername());
            thisUserDto.setBirthdate(thisUser.getBirthdate().toString());
            thisUserDto.setEmail(thisUser.getEmail());
            userService.addFriend(thisUserDto, friendUserDto);

            return "redirect:/lk/friends";
        }
        return "redirect:/users";
    }
}