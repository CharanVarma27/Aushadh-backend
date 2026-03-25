package com.aushadh.controller;

import com.aushadh.model.User;
import com.aushadh.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin
public class AuthController {

    @Autowired
    private AuthService authService;

    // Register
    @PostMapping("/register")
    public User register(@RequestBody User user) {
        return authService.register(user);
    }

    // Login
    @PostMapping("/login")
    public User login(@RequestBody User user) {
        return authService.login(user.getEmail(), user.getPassword());
    }
}