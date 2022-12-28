package com.giftservice.gift_service.service;

import com.giftservice.gift_service.dto.UserDto;
import com.giftservice.gift_service.entities.security.User;

import java.util.List;

public interface UserService {

    void saveUser(UserDto userDto);

    User findUserByEmail(String email);

    List<UserDto> findAllUsers();
}