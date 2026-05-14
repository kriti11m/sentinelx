package com.sentinelx.auth.service;
import com.sentinelx.auth.entity.User;
import com.sentinelx.auth.dto.SignupRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import lombok.RequiredArgsConstructor;
import com.sentinelx.auth.config.SecurityConfig;
import org.springframework.stereotype.Service;
import com.sentinelx.auth.repository.UserRepository;
@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    public void signup(SignupRequest request){
        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole("USER");
        userRepository.save(user);
    }

}
