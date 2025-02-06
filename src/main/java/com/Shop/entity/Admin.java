package com.Shop.entity;

import com.Shop.response.AdminRespDto;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Admin extends AppUser{

    private String gender;

    private String address;

    private String yOfBirth;

    private LocalDateTime dateCreated;

    @Column(updatable = true)
    private LocalDateTime updatedDate;

    @OneToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name = "image_id")
    private AdminImage adminImage; // Relationship to AdminImage entity

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "manager_id")
    private Manager manager;

    public Admin() {
        super();
    }

    public Admin(Long id, List<CustomerOrder> orders, Role role, boolean isActive, String phone,
                 String password, String email, String fullName, String gender, Manager manager,
                 AdminImage adminImage, LocalDateTime updatedDate, LocalDateTime dateCreated, String yOfBirth, String address) {
        super(id, orders, role, isActive, phone, password, email, fullName);
        this.gender = gender;
        this.manager = manager;
        this.adminImage = adminImage;
        this.updatedDate = updatedDate;
        this.dateCreated = dateCreated;
        this.yOfBirth = yOfBirth;
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

    public LocalDateTime getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(LocalDateTime dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getyOfBirth() {
        return yOfBirth;
    }

    public void setyOfBirth(String yOfBirth) {
        this.yOfBirth = yOfBirth;
    }

    public LocalDateTime getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(LocalDateTime updatedDate) {
        this.updatedDate = updatedDate;
    }

    public AdminImage getAdminImage() {
        return adminImage;
    }

    public void setAdminImage(AdminImage adminImage) {
        this.adminImage = adminImage;
    }

    public Manager getManager() {
        return manager;
    }

    public void setManager(Manager manager) {
        this.manager = manager;
    }

    public AdminRespDto getAdminDto(){
        AdminRespDto adminRespDto = new AdminRespDto();
        adminRespDto.setFullName(getFullName());
        adminRespDto.setEmail(getEmail());
        adminRespDto.setAddress(address);
        adminRespDto.setPhone(getPhone());
        adminRespDto.setId(getId());
        adminRespDto.setDateCreated(dateCreated);
        adminRespDto.setDateUpdated(updatedDate);
        adminRespDto.setyOfBirth(yOfBirth);
        adminRespDto.setGender(gender);
        adminRespDto.setPhone(getPhone());
//        adminRespDto.setImageByte(adminImage.getImg());
        // Check if manager is null before accessing fullName
        if (manager != null) {
            adminRespDto.setCreatedByManager(manager.getFullName());
            adminRespDto.setManagerEmail(manager.getEmail());
            adminRespDto.setManagerPhone(manager.getPhone());
        } else {
            adminRespDto.setCreatedByManager("Unknown Manager");
        }
        return adminRespDto;
    }
}
