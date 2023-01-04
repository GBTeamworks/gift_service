package com.giftservice.gift_service.services;

import com.giftservice.gift_service.dto.UserDto;
import com.giftservice.gift_service.entities.security.User;

import java.util.List;

public interface UserService {

    void saveUser(UserDto userDto);

    void updateUser(UserDto userDto);

    User findUserByEmail(String email);

    User findUserByUsername(String username);

    List<UserDto> findAllUsers();
}