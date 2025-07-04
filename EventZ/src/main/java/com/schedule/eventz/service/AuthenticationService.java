package com.schedule.eventz.service;

import com.schedule.eventz.models.AuthenticationResponse;
import com.schedule.eventz.models.User;
import com.schedule.eventz.security.JwtUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    @Value("${jwt.expiration}")
    private Long jwtExpiration;

    public AuthenticationService(UserService userService, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    public AuthenticationResponse login(String email, String password) {
        User user = userService.getUserByEmail(email);
        if (!user.isActive()) {
            throw new RuntimeException("Account is disabled");
        }
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }
        String token = jwtUtil.generateToken(user.getEmail(), user.getRole().name());
        userService.updateLastLogin(user.getId());
        return new AuthenticationResponse(token, user, jwtExpiration);
    }
}
