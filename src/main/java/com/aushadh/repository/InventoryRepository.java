package com.aushadh.repository;

import com.aushadh.model.Inventory;
import com.aushadh.model.Medicine;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {

    List<Inventory> findByMedicine(Medicine medicine);

	List<Inventory> findByPharmacyId(Long id);
}