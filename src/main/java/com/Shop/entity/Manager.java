package com.Shop.entity;

import com.Shop.dto.ManagerProfileDto;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class Manager extends AppUser{

    private String gender;

    private String address;

    @OneToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name = "image_id")
    private AdminImage profImage; // Relationship to ProfileImage entity

    @OneToMany(mappedBy = "manager",fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE} )
    private List<Admin> admin;

    public Manager() {
        super();
    }

    public Manager(Long id, List<CustomerOrder> orders, Role role, boolean isActive, String phone,
                   String password, String email, String fullName, String gender, List<Admin> admin,
                   AdminImage profImage, String address) {
        super(id, orders, role, isActive, phone, password, email, fullName);
        this.gender = gender;
        this.admin = admin;
        this.profImage = profImage;
        this.address = address;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public AdminImage getProfImage() {
        return profImage;
    }

    public void setProfImage(AdminImage profImage) {
        this.profImage = profImage;
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

    public ManagerProfileDto getProfileDto() {
        ManagerProfileDto profileDto = new ManagerProfileDto();

        profileDto.setFullName(getFullName());
        profileDto.setEmail(getEmail());
        profileDto.setPhone(getPhone());
        profileDto.setGender(getGender());
        profileDto.setAddress(getAddress());

        return profileDto;
    }
}
