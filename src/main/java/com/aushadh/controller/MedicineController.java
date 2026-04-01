package com.aushadh.controller;

import com.aushadh.model.Medicine;
import com.aushadh.service.MedicineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/medicines")
public class MedicineController {

    @Autowired
    private MedicineService medicineService;

    @GetMapping("/search")
    public List<Medicine> search(@RequestParam String name) {
        return medicineService.searchMedicine(name);
    }

    @PostMapping("/add")
    public Medicine add(@RequestBody Medicine medicine) {
        return medicineService.saveMedicine(medicine);
    }
}
