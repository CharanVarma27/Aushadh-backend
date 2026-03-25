package com.aushadh.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "reservations")
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "pharmacy_id")
    private Pharmacy pharmacy;

    @ManyToOne
    @JoinColumn(name = "medicine_id")
    private Medicine medicine;

    private int quantity;
    private double totalAmount;
    private String status; // Pending Pickup, Handed Over, Cancelled
    private LocalDateTime reservationDate;

    public Reservation() {}

    public Reservation(User user, Pharmacy pharmacy, Medicine medicine, int quantity, double totalAmount, String status) {
        this.user = user;
        this.pharmacy = pharmacy;
        this.medicine = medicine;
        this.quantity = quantity;
        this.totalAmount = totalAmount;
        this.status = status;
        this.reservationDate = LocalDateTime.now();
    }

    public Long getId() { return id; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public Pharmacy getPharmacy() { return pharmacy; }
    public void setPharmacy(Pharmacy pharmacy) { this.pharmacy = pharmacy; }

    public Medicine getMedicine() { return medicine; }
    public void setMedicine(Medicine medicine) { this.medicine = medicine; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public double getTotalAmount() { return totalAmount; }
    public void setTotalAmount(double totalAmount) { this.totalAmount = totalAmount; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public LocalDateTime getReservationDate() { return reservationDate; }
    public void setReservationDate(LocalDateTime reservationDate) { this.reservationDate = reservationDate; }
}
