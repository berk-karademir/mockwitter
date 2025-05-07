package com.BekoInc.mockwitter.serviceImplementation;

import com.BekoInc.mockwitter.dto.UserEssentialCredentials;
import com.BekoInc.mockwitter.dto.UserLoginRequest;
import com.BekoInc.mockwitter.dto.UserLoginResponse;
import com.BekoInc.mockwitter.dto.UserRegistrationResponse;
import com.BekoInc.mockwitter.entity.user.Role;
import com.BekoInc.mockwitter.entity.user.User;
import com.BekoInc.mockwitter.util.UserRoleType;
import com.BekoInc.mockwitter.exception.MockwitterException;
import com.BekoInc.mockwitter.repository.RoleRepository;
import com.BekoInc.mockwitter.repository.UserRepository;
import com.BekoInc.mockwitter.service.AuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public AuthenticationServiceImpl(UserRepository userRepository,
                                     RoleRepository roleRepository,
                                     PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserRegistrationResponse register(UserEssentialCredentials userCredentials) {
        // E-posta ve kullanıcı adının zaten mevcut olup olmadığını kontrol et
        if (userRepository.findUserByEmail(userCredentials.email()).isPresent()) {
            throw new MockwitterException("Email already in use.", HttpStatus.CONFLICT);
        }

        if (userRepository.findUserByUsername(userCredentials.username()).isPresent()) {
            throw new MockwitterException("Username already in use.", HttpStatus.CONFLICT);
        }

        // Şifreyi şifrele
        String encryptedPassword = passwordEncoder.encode(userCredentials.password());

        // Rolleri al
        Set<Role> roles = new HashSet<>();
        if (userCredentials.roles() == null || userCredentials.roles().isEmpty()) {
            // Eğer roller belirtilmemişse, varsayılan olarak "CASUAL" rolünü ekle
            Optional<Role> casualRole = roleRepository.findByAuthority(UserRoleType.CASUAL.name());
            if (casualRole.isPresent()) {
                roles.add(casualRole.get());
            } else {
                throw new IllegalArgumentException("Default role 'CASUAL' not found.");
            }
        } else {
            // Belirtilen roller üzerinden devam et
            for (UserRoleType roleType : userCredentials.roles()) {
                Optional<Role> optionalRole = roleRepository.findByAuthority(roleType.name());
                if (!optionalRole.isPresent()) {
                    throw new IllegalArgumentException("Role not found: " + roleType.name());
                }
                roles.add(optionalRole.get());
            }
        }

        // Kullanıcıyı oluştur
        User user = new User();
        user.setEmail(userCredentials.email());
        user.setUsername(userCredentials.username());
        user.setPassword(encryptedPassword);
        user.setAuthorities(roles);

        // Opsiyonel alanları kontrol et ve set et
        if (userCredentials.firstName() != null) {
            user.setFirstName(userCredentials.firstName());
        }
        if (userCredentials.lastName() != null) {
            user.setLastName(userCredentials.lastName());
        }
        if (userCredentials.phoneNumber() != null) {
            user.setPhoneNumber(userCredentials.phoneNumber());
        }

        userRepository.save(user);

        return new UserRegistrationResponse(
                user.getId(),
                user.getFirstName(), // null olabilir
                user.getLastName(),  // null olabilir
                user.getEmail(),
                user.getUsername(),
                user.getPhoneNumber(), // null olabilir
                roles
        );
    }


    @Override
    public UserLoginResponse login(UserLoginRequest loginRequest, HttpServletRequest request) {
        String identifier = loginRequest.getEmailOrUsername();
        
        // Önce username ile arama yap
        Optional<User> userOpt = userRepository.findUserByUsername(identifier);
        
        // Eğer username ile bulunamazsa email ile arama yap
        if (!userOpt.isPresent()) {
            userOpt = userRepository.findUserByEmail(identifier);
        }

        if (!userOpt.isPresent()) {
            throw new UsernameNotFoundException("User not found with identifier: " + identifier);
        }

        User user = userOpt.get();
        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new MockwitterException("Invalid credentials", HttpStatus.UNAUTHORIZED);
        }

        // Spring Security kimlik doğrulamasını başlat
        UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken(user.getUsername(), null, user.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(authToken);

        // Oturumu başlat ve JSESSIONID'yi ayarla
        request.getSession().setAttribute("user", user);
        
        return new UserLoginResponse(user.getId(), user.getUsername());
    }
}