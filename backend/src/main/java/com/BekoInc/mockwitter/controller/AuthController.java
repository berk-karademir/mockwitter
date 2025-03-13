package com.BekoInc.mockwitter.controller;


import com.BekoInc.mockwitter.dto.UserEssentialCredentials;
import com.BekoInc.mockwitter.dto.UserLoginRequest;
import com.BekoInc.mockwitter.dto.UserRegistrationResponse;
import com.BekoInc.mockwitter.service.AuthenticationService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
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


//    @PostMapping("/login")
//    public UserLoginResponse login(@RequestBody UserLoginRequest loginRequest, HttpServletRequest request) {
//        UserLoginResponse log = authenticationService.login(loginRequest, request);
//
//        if (!(log ==null)) {
//            // Oturum oluşturulmuşsa kullanıcıya başarılı mesajı döndürülür
//            return new UserLoginResponse(request.changeSessionId());
//        } else {
//            // Başarısız giriş için hata mesajı
//            return new UserLoginResponse("Geçersiz kullanıcı adı veya şifreeeee.");
//        }
//    }

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
