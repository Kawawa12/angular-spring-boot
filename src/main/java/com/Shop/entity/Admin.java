package com.Shop.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;

@Entity
public class Admin extends AppUser{

    private String gender;

    private String address;

    @Column(nullable = true)
    private String imageUrl;

    public Admin() {
        super();
    }

    public Admin(String gender, String address, String imageUrl) {
        this.gender = gender;
        this.address = address;
        this.imageUrl = imageUrl;
    }

    public Admin(Long id, String fullName, String email, String password, String phone, Role role, String gender, String address, String imageUrl) {
        super(id, fullName, email, password, phone, role);
        this.gender = gender;
        this.address = address;
        this.imageUrl = imageUrl;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
