package com.sentinelx.account.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/account")
public class AccountController {

    @GetMapping("/balance")
    public String balance() {

        return "Account Balance API";
    }
}