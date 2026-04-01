package com.aushadh.controller;

import com.aushadh.model.Pharmacy;
import com.aushadh.model.User;
import com.aushadh.repository.PharmacyRepository;
import com.aushadh.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PharmacyRepository pharmacyRepository;

    @GetMapping("/stats")
    public Map<String, Object> getStats() {
        Map<String, Object> stats = new HashMap<>();
        long totalUsers = userRepository.count();
        long totalPharmacies = pharmacyRepository.count();
        List<Pharmacy> allPharmacies = pharmacyRepository.findAll();
        long pendingPharmacies = allPharmacies.stream().filter(p -> !p.isVerified()).count();
        long activePharmacies = allPharmacies.stream().filter(Pharmacy::isVerified).count();

        stats.put("totalUsers", totalUsers);
        stats.put("totalPharmacies", totalPharmacies);
        stats.put("pendingPharmacies", pendingPharmacies);
        stats.put("activePharmacies", activePharmacies);

        return stats;
    }

    @GetMapping("/pharmacies")
    public List<Pharmacy> getAllPharmacies() {
        return pharmacyRepository.findAll();
    }

    @PutMapping("/pharmacies/{id}/approve")
    public ResponseEntity<?> approvePharmacy(@PathVariable Long id) {
        Optional<Pharmacy> opt = pharmacyRepository.findById(id);
        if (opt.isPresent()) {
            Pharmacy p = opt.get();
            p.setVerified(true);
            pharmacyRepository.save(p);
            return ResponseEntity.ok().body(Map.of("message", "Pharmacy Verified"));
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
