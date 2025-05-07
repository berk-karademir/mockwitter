package com.BekoInc.mockwitter.config;

import com.BekoInc.mockwitter.util.UserRoleType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfiguration {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(UserDetailsService userDetailsService) {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return new ProviderManager(daoAuthenticationProvider);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .cors(Customizer.withDefaults()) // CORS yapılandırmasını aktifleştir
                .csrf(csrf -> csrf.disable()) // CSRF koruması devre dışı
                .authorizeHttpRequests(auth -> {

//                    // Herkese açık endpointler
//                    auth.requestMatchers("/public/**", "/auth/**", "/roles/**", "/home/**").permitAll();
//                    auth.requestMatchers(HttpMethod.GET, "/tweet/findByUserId/**", "/tweet/findById/**").permitAll();
//                    auth.requestMatchers(HttpMethod.GET,  "/tweet/findById/**").permitAll();
//                    auth.requestMatchers(HttpMethod.GET,  "/home").permitAll();
//
//                    // Yetkilendirme gerektiren endpointler
//                    auth.requestMatchers("/tweet/**", "/reply/**", "/like/**", "/comment/**", "/retweet/**", "/user/**")
//                            .hasAnyAuthority(UserRoleType.CASUAL.name(), UserRoleType.COMPANY.name(), UserRoleType.ADMIN.name());
//
//                    // Sadece admin rolüne açık endpointler
//                    auth.requestMatchers("/admin/**").hasAuthority(UserRoleType.ADMIN.name());
//
//                    // Diğer tüm istekler kimlik doğrulama gerektirir
                   auth.anyRequest().permitAll();
               })
                .formLogin(Customizer.withDefaults())
                .httpBasic(Customizer.withDefaults())
                .build();
    }
}
