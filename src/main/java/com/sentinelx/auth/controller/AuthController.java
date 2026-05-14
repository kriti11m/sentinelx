package com.sentinelx.auth.controller;
import lombok.RequiredArgsConstructor;
import com.sentinelx.auth.dto.SignupRequest;
import com.sentinelx.auth.service.AuthService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    @PostMapping("/signup")
    public String signup(@RequestBody SignupRequest request){
        authService.signup(request);
        return "User Registered successful";
    }
}

