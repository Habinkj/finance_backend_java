package com.example.demo.service;

import com.example.demo.entity.UserEntity;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder; // 🔥 IMPORT THIS
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository repo;

    @Autowired
    private BCryptPasswordEncoder encoder; // 🔥 AUTOWIRED THE ENCODER

    public UserEntity register(UserEntity user) {
        // 🔥 HASH THE PASSWORD BEFORE SAVING
        // This turns "password123" into a secure string like "$2a$10$..."
        user.setPassword(encoder.encode(user.getPassword()));
        return repo.save(user);
    }

    public UserEntity login(String email, String password) {
        UserEntity user = repo.findByEmail(email);

        // 🔥 USE encoder.matches() INSTEAD OF .equals()
        // You cannot use .equals() because the DB has a hash and the input is plain text.
        if (user == null || !encoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        return user;
    }
}