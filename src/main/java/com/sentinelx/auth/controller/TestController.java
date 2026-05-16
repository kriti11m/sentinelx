package com.sentinelx.auth.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/test")
public class TestController {

    @GetMapping
    public String test() {

        return "Protected API Accessed";
    }
}