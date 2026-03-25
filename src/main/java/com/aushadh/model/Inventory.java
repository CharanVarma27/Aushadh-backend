package com.aushadh.model;

import jakarta.persistence.*;

@Entity
@Table(name = "inventory")
public class Inventory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Pharmacy pharmacy;

    @ManyToOne
    private Medicine medicine;

    private int stock;
    private double price;

    public Inventory() {}

    public Inventory(Pharmacy pharmacy, Medicine medicine, int stock, double price) {
        this.pharmacy = pharmacy;
        this.medicine = medicine;
        this.stock = stock;
        this.price = price;
    }

    public Long getId() { return id; }

    public Pharmacy getPharmacy() { return pharmacy; }
    public void setPharmacy(Pharmacy pharmacy) { this.pharmacy = pharmacy; }

    public Medicine getMedicine() { return medicine; }
    public void setMedicine(Medicine medicine) { this.medicine = medicine; }

    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
}