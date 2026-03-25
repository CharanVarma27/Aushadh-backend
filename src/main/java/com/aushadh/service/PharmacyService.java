package com.aushadh.service;

import com.aushadh.model.Inventory;
import com.aushadh.model.Medicine;
import com.aushadh.repository.InventoryRepository;
import com.aushadh.repository.MedicineRepository;
import com.aushadh.model.Pharmacy;
import com.aushadh.repository.PharmacyRepository;
import com.aushadh.util.DistanceUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PharmacyService {

    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    private MedicineRepository medicineRepository;

    @Autowired
    private PharmacyRepository pharmacyRepository;

    public List<Map<String, Object>> findNearbyPharmacies(String medicineName,
            double userLat,
            double userLng) {

        List<Map<String, Object>> result = new ArrayList<>();

        List<Medicine> medicines = medicineRepository.findByNameContainingIgnoreCase(medicineName);

        for (Medicine medicine : medicines) {

            List<Inventory> inventoryList = inventoryRepository.findByMedicine(medicine);

            for (Inventory inv : inventoryList) {

                double distance = DistanceUtil.calculate(
                        userLat, userLng,
                        inv.getPharmacy().getLatitude(),
                        inv.getPharmacy().getLongitude());

                Map<String, Object> map = new HashMap<>();
                map.put("pharmacy", inv.getPharmacy().getName());
                map.put("distance", distance);
                String status;

                if (inv.getStock() == 0) {
                    status = "Out of Stock";
                } else if (inv.getStock() < 10) {
                    status = "Low Stock";
                } else {
                    status = "Available";
                }

                map.put("stock", inv.getStock());
                map.put("status", status);
                map.put("price", inv.getPrice());

                result.add(map);
            }
        }

        result.sort(Comparator.comparingDouble(o -> (double) o.get("distance")));
        result.sort(Comparator.comparingDouble(o -> (double) o.get("price")));

        return result;
    }

    public List<Map<String, Object>> findAllNearbyPharmacies(double userLat, double userLng) {
        List<Map<String, Object>> result = new ArrayList<>();
        
        List<Pharmacy> allPharmacies = pharmacyRepository.findAll();
        
        for (Pharmacy pharmacy : allPharmacies) {
            double distance = DistanceUtil.calculate(
                    userLat, userLng,
                    pharmacy.getLatitude(),
                    pharmacy.getLongitude()
            );

            Map<String, Object> map = new HashMap<>();
            map.put("pharmacyId", pharmacy.getId());
            map.put("pharmacy", pharmacy.getName());
            map.put("distance", distance);
            map.put("lat", pharmacy.getLatitude());
            map.put("lng", pharmacy.getLongitude());
            map.put("address", pharmacy.getAddress());
            map.put("status", "Available"); // Default status for generic query
            
            result.add(map);
        }
        
        result.sort(Comparator.comparingDouble(o -> (double) o.get("distance")));
        return result;
    }
}