package com.aushadh.service;

import com.aushadh.model.Medicine;
import com.aushadh.repository.MedicineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedicineService {

    @Autowired
    private MedicineRepository medicineRepository;

    public List<Medicine> searchMedicine(String name) {
        return medicineRepository.findByNameContainingIgnoreCase(name);
    }

    public Medicine saveMedicine(Medicine medicine) {
        return medicineRepository.save(medicine);
    }
}