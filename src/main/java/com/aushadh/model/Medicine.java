package com.aushadh.model;

import jakarta.persistence.*;

@Entity
@Table(name = "medicine")
public class Medicine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private String category;
    private String genericName;
    private boolean emergencyFlag;

    // Constructor
    public Medicine() {}

    public Medicine(String name, String description, String category, String genericName, boolean emergencyFlag) {
        this.name = name;
        this.description = description;
        this.category = category;
        this.genericName = genericName;
        this.emergencyFlag = emergencyFlag;
    }

    // Getters & Setters

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getGenericName() {
        return genericName;
    }

    public void setGenericName(String genericName) {
        this.genericName = genericName;
    }

    public boolean isEmergencyFlag() {
        return emergencyFlag;
    }

    public void setEmergencyFlag(boolean emergencyFlag) {
        this.emergencyFlag = emergencyFlag;
    }
}