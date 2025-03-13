package com.BekoInc.mockwitter.repository;

import com.BekoInc.mockwitter.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT u FROM User u WHERE u.username=:username")
    Optional<User> findUserByUsername(String username);

    @Query("SELECT u FROM User u WHERE u.email=:email")
    Optional<User> findUserByEmail(String email);




}