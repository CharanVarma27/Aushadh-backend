package com.aushadh.service;

import com.aushadh.model.User;
import com.aushadh.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    public User register(User user) {
        user.setRole(user.getRole().toUpperCase());
        return userRepository.save(user);
    }

    public User login(String email, String password) {

        Optional<User> userOpt = userRepository.findByEmail(email);

        if (userOpt.isPresent()) {
            User user = userOpt.get();

            if (user.getPassword().equals(password)) {
                return user;
            }
        }

        throw new RuntimeException("Invalid credentials");
    }
}