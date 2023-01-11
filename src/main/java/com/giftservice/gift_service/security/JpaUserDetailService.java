package com.giftservice.gift_service.security;

import com.giftservice.gift_service.entities.security.User;
import com.giftservice.gift_service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class JpaUserDetailService implements UserDetailsService {

    private final UserRepository userDao;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User users = userDao.findByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException("Username: " + username + "not found!")
        );
        return users;
    }
}
