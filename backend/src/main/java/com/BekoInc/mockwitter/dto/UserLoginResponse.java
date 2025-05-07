package com.BekoInc.mockwitter.dto;

public record UserLoginResponse(
        Long userId,
        String username
) {
    public UserLoginResponse() {
        this(null, null);
    }
}
