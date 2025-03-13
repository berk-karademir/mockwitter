package com.BekoInc.mockwitter.serviceImplementation;

import com.BekoInc.mockwitter.entity.user.Role;
import com.BekoInc.mockwitter.entity.user.User;
import com.BekoInc.mockwitter.exception.MockwitterException;
import com.BekoInc.mockwitter.repository.UserRepository;
import com.BekoInc.mockwitter.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findUserByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User  not found with username: " + username));
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userRepository.findUserByUsername(username);
    }

    @Override
    public User createUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User updateUser (Long id, User user) {
        User existingUser  = userRepository.findById(id)
                .orElseThrow(() ->new MockwitterException("zzz Can not found user with id " + id, HttpStatus.NOT_FOUND));


        // Kullanıcı bilgilerini güncelle
        if (user.getFirstName() != null) {
            existingUser .setFirstName(user.getFirstName());
        }
        if (user.getLastName() != null) {
            existingUser .setLastName(user.getLastName());
        }
        if (user.getEmail() != null) {
            existingUser .setEmail(user.getEmail());
        }
        if (user.getUsername() != null) {
            existingUser .setUsername(user.getUsername());
        }
        if (user.getPassword() != null) {
            existingUser .setPassword(user.getPassword());
        }
        if (user.getPhoneNumber() != null) {
            existingUser .setPhoneNumber(user.getPhoneNumber());
        }
        if (user.getAuthorities() != null && !user.getAuthorities().isEmpty()) {
            // Burada authorities'leri Collection<Role> olarak ayarlıyoruz
            Collection<Role> roles = new HashSet<>();
            for (GrantedAuthority authority : user.getAuthorities()) {
                if (authority instanceof Role) {
                    roles.add((Role) authority);
                }
            }
            existingUser .setAuthorities(roles);
        }

        return userRepository.save(existingUser );
    }

    @Override
    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new MockwitterException("User not found with id: " + id, HttpStatus.NOT_FOUND));
        userRepository.delete(user);
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new MockwitterException("User not found with id: " + id, HttpStatus.NOT_FOUND));
    }
}