package com.aushadh;

import com.aushadh.model.*;
import com.aushadh.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class DataSeeder implements CommandLineRunner {

    private final PharmacyRepository pharmacyRepository;
    private final UserRepository userRepository;
    private final MedicineRepository medicineRepository;
    private final InventoryRepository inventoryRepository;
    private final ReservationRepository reservationRepository;
    private final SystemSettingRepository systemSettingRepository;

    public DataSeeder(PharmacyRepository pharmacyRepository,
                      UserRepository userRepository,
                      MedicineRepository medicineRepository,
                      InventoryRepository inventoryRepository,
                      ReservationRepository reservationRepository,
                      SystemSettingRepository systemSettingRepository) {
        this.pharmacyRepository = pharmacyRepository;
        this.userRepository = userRepository;
        this.medicineRepository = medicineRepository;
        this.inventoryRepository = inventoryRepository;
        this.reservationRepository = reservationRepository;
        this.systemSettingRepository = systemSettingRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        // Only seed if database is empty
        if (pharmacyRepository.count() == 0) {
            seedData();
        }
    }

    private void seedData() {
        // 1. Create a Default Pharmacy (ID will be 1)
        Pharmacy pharmacy = new Pharmacy(
            "City Care Pharmacy", 
            "123 Main St, Hyderabad", 
            "Hyderabad", 
            17.3850, 
            78.4867, 
            "040-12345678", 
            null, // ownerId for seeded pharmacy
            true, 
            4.5
        );
        pharmacy = pharmacyRepository.save(pharmacy);

        // 2. Create Default Users (Admin & Pharmacy Owner)
        User admin = new User("Admin", "admin@aushadh.com", "admin123", "ADMIN");
        userRepository.save(admin);

        User owner = new User("Charan Varma", "charan@aushadh.com", "password123", "PHARMACY");
        userRepository.save(owner);

        // 3. Create Sample Medicines
        Medicine m1 = new Medicine("Paracetamol 500mg", "Generic", "N/A", "Analgesic", false);
        Medicine m2 = new Medicine("Amoxicillin 250mg", "Antibiotic", "N/A", "Antibiotic", true);
        Medicine m3 = new Medicine("Cetirizine 10mg", "Generic", "N/A", "Antihistamine", false);
        
        medicineRepository.saveAll(Arrays.asList(m1, m2, m3));

        // 4. Link Medicines to Pharmacy Inventory
        Inventory inv1 = new Inventory(pharmacy, m1, 120, 45.0);
        Inventory inv2 = new Inventory(pharmacy, m2, 50, 150.0);
        Inventory inv3 = new Inventory(pharmacy, m3, 15, 30.0); // Low stock sample
        
        inventoryRepository.saveAll(Arrays.asList(inv1, inv2, inv3));

        Reservation res = new Reservation(owner, pharmacy, m1, 1, 45.0, "Pending Pickup");
        reservationRepository.save(res);

        // 6. Create Default System Settings
        systemSettingRepository.saveAll(Arrays.asList(
            new SystemSetting("enable_map_search", "true"),
            new SystemSetting("auto_approval", "false"),
            new SystemSetting("maintenance_mode", "false")
        ));

        System.out.println(">>> Database Seeded with default Pharmacy (ID 1) and sample data.");
    }
}
