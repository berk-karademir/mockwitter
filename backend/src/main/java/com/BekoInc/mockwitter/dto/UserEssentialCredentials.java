package com.BekoInc.mockwitter.dto;

import com.BekoInc.mockwitter.util.UserRoleType;
import jakarta.annotation.Nullable;

import java.util.Set;

public record UserEssentialCredentials(
        @Nullable String firstName ,
        @Nullable String lastName,
        String email,
        String username,
        String password,
        @Nullable String phoneNumber,
        Set<UserRoleType> roles) {
}