package com.BekoInc.mockwitter.service;

import com.BekoInc.mockwitter.dto.UserEssentialCredentials;
import com.BekoInc.mockwitter.dto.UserLoginRequest;
import com.BekoInc.mockwitter.dto.UserRegistrationResponse;
import jakarta.servlet.http.HttpServletRequest;

public interface AuthenticationService {
    UserRegistrationResponse register(UserEssentialCredentials userEssentialCredentials);
//    UserLoginResponse login(UserLoginRequest loginRequest, HttpServletRequest request);

}