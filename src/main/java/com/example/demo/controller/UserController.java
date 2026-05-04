package com.example.demo.controller;

import com.example.demo.config.JwtUtil;
import com.example.demo.entity.UserEntity;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService service;

    @Autowired
    private JwtUtil jwtUtil; // 🔥 Properly injected as a Spring Bean

    @PostMapping("/register")
    public UserEntity register(@RequestBody UserEntity user) {
        return service.register(user);
    }

    @PostMapping("/login")
    public String login(@RequestBody UserEntity user) {
        UserEntity loggedInUser = service.login(user.getEmail(), user.getPassword());

        // 🔥 Now passing the role into the token
        return jwtUtil.generateToken(loggedInUser.getEmail(), loggedInUser.getRole());
    }
}