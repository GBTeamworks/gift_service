package com.giftservice.gift_service.services;

import com.giftservice.gift_service.dto.UserDto;
import com.giftservice.gift_service.entities.security.Authority;
import com.giftservice.gift_service.entities.security.User;
import com.giftservice.gift_service.repository.AuthorityRepository;
import com.giftservice.gift_service.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final AuthorityRepository authorityRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository,
                           AuthorityRepository authorityRepository,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.authorityRepository = authorityRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void save(UserDto userDto) {
        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setEmail(userDto.getEmail());
        LocalDate localDate = LocalDate.parse(userDto.getBirthdate());
        user.setBirthdate(localDate);

        user.setPassword(passwordEncoder.encode(userDto.getPassword()));

        Authority role = authorityRepository.findByRole("USER");
        if (role == null) {
            role = checkRoleExist();
        }
        user.setAuthorities(Set.of(role));
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void addFriend(UserDto thisUserDto, UserDto friendUserDto) {

        User thisUser = userRepository.findByEmail(thisUserDto.getEmail());
        User friend = userRepository.findByEmail(friendUserDto.getEmail());

        thisUser.getSubscribers().add(friend);

        userRepository.save(thisUser);
    }

    @Override
    @Transactional
    public void deleteFriend(UserDto thisUserDto, UserDto friendUserDto) {

        User thisUser = userRepository.findByEmail(thisUserDto.getEmail());
        User friend = userRepository.findByEmail(friendUserDto.getEmail());

        thisUser.getSubscribers().remove(friend);

        userRepository.save(thisUser);
    }

    @Override
    public void update(UserDto userDto) {

        User user = userRepository.findById(userDto.getId()).get();
        user.setUsername(userDto.getUsername());
        user.setEmail(userDto.getEmail());
        LocalDate localDate = LocalDate.parse(userDto.getBirthdate());
        user.setBirthdate(localDate);

        userRepository.save(user);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public List<UserDto> findAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(this::mapToUserDto)
                .collect(Collectors.toList());
    }

    private UserDto mapToUserDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setUsername(user.getUsername());
        userDto.setBirthdate(user.getBirthdate().toString());
        userDto.setEmail(user.getEmail());
        return userDto;
    }

    private Authority checkRoleExist() {
        Authority role = new Authority();
        role.setRole("USER");
        return authorityRepository.save(role);
    }
}
