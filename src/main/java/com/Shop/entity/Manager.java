package com.Shop.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Manager extends AppUser{

    private String gender;

    private String address;


    @Lob
    private byte[] img;


    @OneToMany(mappedBy = "manager",fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE} )
    private List<Admin> admin;

    public Manager() {
        super();
    }


    public Manager(Long id, List<CustomerOrder> orders, Role role, boolean isActive, String phone, String password, String email, String fullName, List<Admin> admin, byte[] img, String address, String gender) {
        super(id, orders, role, isActive, phone, password, email, fullName);
        this.admin = admin;
        this.img = img;
        this.address = address;
        this.gender = gender;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public byte[] getImg() {
        return img;
    }

    public void setImg(byte[] img) {
        this.img = img;
    }

    public List<Admin> getAdmin() {
        return admin;
    }

    public void setAdmin(List<Admin> admin) {
        this.admin = admin;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
