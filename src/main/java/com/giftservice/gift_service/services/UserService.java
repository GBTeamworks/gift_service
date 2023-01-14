package com.giftservice.gift_service.services;

import com.giftservice.gift_service.dto.UserDto;
import com.giftservice.gift_service.entities.security.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    void save(UserDto userDto);

    void update(UserDto userDto);

    void addFriend(UserDto thisUserDto, UserDto friendUserDto);

    void deleteFriend(UserDto thisUserDto, UserDto friendUserDto);

    User findByEmail(String email);

    Optional<User> findByUsername(String username);

    List<UserDto> findAllUsers();
}