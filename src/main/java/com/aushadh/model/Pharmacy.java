package com.aushadh.model;

import jakarta.persistence.*;

@Entity
@Table(name = "pharmacy")
public class Pharmacy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String address;
    private String city;

    private double latitude;
    private double longitude;

    private String phone;
    private Long ownerId;

    private boolean verified;
    private double rating;

    public Pharmacy() {}

    public Pharmacy(String name, String address, String city,
                    double latitude, double longitude,
                    String phone, Long ownerId, boolean verified, double rating) {
        this.name = name;
        this.address = address;
        this.city = city;
        this.latitude = latitude;
        this.longitude = longitude;
        this.phone = phone;
        this.ownerId = ownerId;
        this.verified = verified;
        this.rating = rating;
    }

    public Long getId() { return id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public double getLatitude() { return latitude; }
    public void setLatitude(double latitude) { this.latitude = latitude; }

    public double getLongitude() { return longitude; }
    public void setLongitude(double longitude) { this.longitude = longitude; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public boolean isVerified() { return verified; }
    public void setVerified(boolean verified) { this.verified = verified; }

    public double getRating() { return rating; }
    public void setRating(double rating) { this.rating = rating; }

    public Long getOwnerId() { return ownerId; }
    public void setOwnerId(Long ownerId) { this.ownerId = ownerId; }
}