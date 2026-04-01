package com.aushadh.controller;

import com.aushadh.service.PharmacyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/pharmacies")
@CrossOrigin(origins = "https://aushadh.onrender.com/")
public class PharmacyController {

    @Autowired
    private PharmacyService pharmacyService;

    @GetMapping("/nearby")
    public List<Map<String, Object>> getNearby(
            @RequestParam String medicine,
            @RequestParam double lat,
            @RequestParam double lng) {

        return pharmacyService.findNearbyPharmacies(medicine, lat, lng);
    }

    @GetMapping("/all-nearby")
    public List<Map<String, Object>> getAllNearby(
            @RequestParam double lat,
            @RequestParam double lng) {
        return pharmacyService.findAllNearbyPharmacies(lat, lng);
    }
}
