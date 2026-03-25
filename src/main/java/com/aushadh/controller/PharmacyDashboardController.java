package com.aushadh.controller;

import com.aushadh.model.Inventory;
import com.aushadh.model.Medicine;
import com.aushadh.model.Pharmacy;
import com.aushadh.model.Reservation;
import com.aushadh.repository.InventoryRepository;
import com.aushadh.repository.MedicineRepository;
import com.aushadh.repository.PharmacyRepository;
import com.aushadh.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/pharmacy-admin")
@CrossOrigin(origins = "http://localhost:5173")
public class PharmacyDashboardController {

    @Autowired
    private PharmacyRepository pharmacyRepository;

    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    private MedicineRepository medicineRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    @GetMapping("/{id}/stats")
    public Map<String, Object> getStats(@PathVariable Long id) {
        Map<String, Object> stats = new HashMap<>();
        
        List<Reservation> reservations = reservationRepository.findByPharmacyId(id);
        double totalRevenue = reservations.stream()
            .filter(r -> "Handed Over".equals(r.getStatus()))
            .mapToDouble(Reservation::getTotalAmount)
            .sum();

        long pendingReservations = reservations.stream()
            .filter(r -> !"Handed Over".equals(r.getStatus()) && !"Cancelled".equals(r.getStatus()))
            .count();

        List<Inventory> inventory = inventoryRepository.findByPharmacyId(id);
        long lowStockCount = inventory.stream().filter(i -> i.getStock() <= 20).count();

        stats.put("totalRevenue", totalRevenue);
        stats.put("pendingReservations", pendingReservations);
        stats.put("lowStockCount", lowStockCount);

        return stats;
    }

    @GetMapping("/{id}/inventory")
    public List<Map<String, Object>> getInventory(@PathVariable Long id) {
        List<Inventory> inventoryList = inventoryRepository.findByPharmacyId(id);
        return inventoryList.stream().map(inv -> {
            Map<String, Object> map = new HashMap<>();
            map.put("key", inv.getId().toString());
            map.put("name", inv.getMedicine() != null ? inv.getMedicine().getName() : "Unknown");
            map.put("stock", inv.getStock());
            map.put("price", inv.getPrice());
            map.put("status", inv.getStock() > 20 ? "In Stock" : "Low Stock");
            return map;
        }).collect(Collectors.toList());
    }

    @PostMapping("/{id}/inventory")
    public ResponseEntity<?> addMedicine(@PathVariable Long id, @RequestBody Map<String, Object> payload) {
        Optional<Pharmacy> pOpt = pharmacyRepository.findById(id);
        if (pOpt.isEmpty()) return ResponseEntity.notFound().build();

        String name = (String) payload.get("name");
        int stock = Integer.parseInt(payload.get("stock").toString());
        double price = Double.parseDouble(payload.get("price").toString());

        // Simple medicine creation or fetch
        Medicine med = new Medicine(name, "Generic", "N/A", name, false);
        med = medicineRepository.save(med);

        Inventory inv = new Inventory(pOpt.get(), med, stock, price);
        inventoryRepository.save(inv);

        return ResponseEntity.ok(Map.of("message", "Added to inventory"));
    }

    @PutMapping("/inventory/{invId}/restock")
    public ResponseEntity<?> restock(@PathVariable Long invId, @RequestParam int amount) {
        Optional<Inventory> opt = inventoryRepository.findById(invId);
        if (opt.isPresent()) {
            Inventory inv = opt.get();
            inv.setStock(inv.getStock() + amount);
            inventoryRepository.save(inv);
            return ResponseEntity.ok(Map.of("message", "Restocked successfully"));
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/{id}/reservations")
    public List<Map<String, Object>> getReservations(@PathVariable Long id) {
        List<Reservation> resList = reservationRepository.findByPharmacyId(id);
        return resList.stream().map(res -> {
            Map<String, Object> map = new HashMap<>();
            map.put("id", res.getId());
            map.put("customer", res.getUser() != null ? res.getUser().getName() : "Unknown");
            map.put("medicine", res.getMedicine() != null ? res.getMedicine().getName() : "Unknown");
            if (res.getReservationDate() != null) {
                map.put("date", res.getReservationDate().toString());
            } else {
                map.put("date", "N/A");
            }
            map.put("status", res.getStatus());
            map.put("amount", res.getTotalAmount());
            return map;
        }).collect(Collectors.toList());
    }

    @PutMapping("/reservations/{resId}/status")
    public ResponseEntity<?> updateReservationStatus(@PathVariable Long resId, @RequestParam String status) {
        Optional<Reservation> resOpt = reservationRepository.findById(resId);
        if (resOpt.isPresent()) {
            Reservation res = resOpt.get();
            res.setStatus(status);
            reservationRepository.save(res);
            return ResponseEntity.ok(Map.of("message", "Status updated"));
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/{id}/settings")
    public ResponseEntity<?> getPharmacySettings(@PathVariable Long id) {
        return pharmacyRepository.findById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}/settings")
    public ResponseEntity<?> updatePharmacySettings(@PathVariable Long id, @RequestBody Pharmacy updated) {
        return pharmacyRepository.findById(id).map(p -> {
            p.setName(updated.getName());
            p.setAddress(updated.getAddress());
            p.setPhone(updated.getPhone());
            p.setCity(updated.getCity());
            pharmacyRepository.save(p);
            return ResponseEntity.ok(p);
        }).orElse(ResponseEntity.notFound().build());
    }
}
