package com.sentinelx.auth.service;

import com.sentinelx.auth.dto.*;
import com.sentinelx.auth.entity.User;
import com.sentinelx.auth.repository.UserRepository;
import com.sentinelx.auth.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication
        .AuthenticationManager;
import org.springframework.security.authentication
        .UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password
        .PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    private final AuthenticationManager
            authenticationManager;

    public AuthResponse signup(SignupRequest request) {

        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists");
        }

        User user = new User();

        user.setName(request.getName());
        user.setEmail(request.getEmail());

        user.setPassword(
                passwordEncoder.encode(request.getPassword())
        );

        user.setRole("USER");

        userRepository.save(user);

        String token = jwtService.generateToken(user.getEmail());

        return AuthResponse.builder()
                .token(token)
                .email(user.getEmail())
                .role(user.getRole())
                .build();
    }

    public AuthResponse login(LoginRequest request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        User user = userRepository.findByEmail(
                request.getEmail()
        ).orElseThrow();

        String jwtToken =
                jwtService.generateToken(user.getEmail());

        return AuthResponse.builder()
                .token(jwtToken)
                .email(user.getEmail())
                .role(user.getRole())
                .build();
    }
}