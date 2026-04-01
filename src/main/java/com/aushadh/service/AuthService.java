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

    @Autowired
    private com.aushadh.repository.PharmacyRepository pharmacyRepository;

    public User register(User user) {
        user.setRole(user.getRole().toUpperCase());
        User savedUser = userRepository.save(user);

        if ("PHARMACY".equals(savedUser.getRole())) {
            com.aushadh.model.Pharmacy pharmacy = new com.aushadh.model.Pharmacy();
            pharmacy.setName("New Pharmacy - Please Update Settings");
            pharmacy.setOwnerId(savedUser.getId());
            pharmacy.setVerified(false);
            pharmacyRepository.save(pharmacy);
        }

        return savedUser;
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