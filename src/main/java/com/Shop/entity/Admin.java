package com.Shop.entity;

import jakarta.persistence.Entity;

@Entity
public class Admin extends AppUser{

    private String gender;

    private String address;

    public Admin() {
        super();
    }

    public Admin(Long id, String fullName, String email, String password, String phone, Role role,
                 Long id1, String gender, String address) {
        super(id, fullName, email, password, phone, role);
        this.id = id1;
        this.gender = gender;
        this.address = address;
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

}
