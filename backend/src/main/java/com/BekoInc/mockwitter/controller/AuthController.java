package com.BekoInc.mockwitter.controller;

import com.BekoInc.mockwitter.dto.UserEssentialCredentials;
import com.BekoInc.mockwitter.dto.UserLoginRequest;
import com.BekoInc.mockwitter.dto.UserLoginResponse;
import com.BekoInc.mockwitter.dto.UserRegistrationResponse;
import com.BekoInc.mockwitter.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@CrossOrigin(origins = "http://localhost:5173", 
              allowedHeaders = "*", 
              allowCredentials = "true")
@RequestMapping("/auth")
public class AuthController {

    private AuthenticationService authenticationService;


    @Autowired
    public AuthController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }


    @PostMapping("/register")
    public UserRegistrationResponse register(@RequestBody UserEssentialCredentials userRegistration) {
        return authenticationService.register(userRegistration);
    }


    @PostMapping("/login")
    public ResponseEntity<UserLoginResponse> login(@RequestBody UserLoginRequest loginRequest, HttpServletRequest request, HttpServletResponse response) {
        UserLoginResponse loginResponse = authenticationService.login(loginRequest, request);

        // JSESSIONID'yi cookie olarak gönder
        Cookie sessionCookie = new Cookie("JSESSIONID", request.getSession().getId());
        sessionCookie.setHttpOnly(true);  // Güvenlik için
        sessionCookie.setSecure(true);    // HTTPS üzerinden gönder
        sessionCookie.setPath("/");        // Tüm uygulama için geçerli
        response.addCookie(sessionCookie);

        return ResponseEntity.ok(loginResponse);
    }

    @PostMapping("/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        request.getSession().invalidate(); // Oturumu sonlandır
        SecurityContextHolder.clearContext(); // Spring Security kimlik doğrulamasını temizle

        // JSESSIONID çerezini sil
        Cookie cookie = new Cookie("JSESSIONID", null);
        cookie.setPath("/"); // Çerezin geçerli olduğu yolu ayarlayın
        cookie.setMaxAge(0); // Çerezi silmek için süresini 0 yapın
        response.addCookie(cookie);
    }

}
