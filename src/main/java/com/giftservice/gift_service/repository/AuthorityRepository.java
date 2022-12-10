package com.giftservice.gift_service.repository;

import com.giftservice.gift_service.dao.UserDao;
import com.giftservice.gift_service.entities.security.Authority;
import com.giftservice.gift_service.entities.security.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

public interface AuthorityRepository extends JpaRepository<Authority, Long> {

    Authority findByRole(String role);
}
