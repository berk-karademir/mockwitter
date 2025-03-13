package com.BekoInc.mockwitter.service;

import com.BekoInc.mockwitter.entity.user.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

public interface UserService extends UserDetailsService {

    User createUser (User user);
    User updateUser (Long id, User user);
    void deleteUser (Long id);
    User getUserById(Long id);

    Optional<User> findByUsername(String username);
    User loadUserByUsername(String username) throws UsernameNotFoundException;

}