package com.aushadh.repository;

import com.aushadh.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findByPharmacyId(Long pharmacyId);
    List<Reservation> findByUserId(Long userId);
    long countByPharmacyIdAndStatus(Long pharmacyId, String status);
}
