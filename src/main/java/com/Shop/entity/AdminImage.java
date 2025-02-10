package com.Shop.entity;

import com.Shop.dto.AdminDto;
import com.Shop.dto.AdminImageDto;
import jakarta.persistence.*;

@Entity
public class AdminImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    private byte[] img;

    @OneToOne(mappedBy = "adminImage", fetch = FetchType.EAGER)
    private Admin admin;

    @OneToOne(mappedBy = "profImage", fetch = FetchType.EAGER)
    private Manager manager;

    public AdminImage() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Admin getAdmin() {
        return admin;
    }

    public Manager getManager() {
        return manager;
    }

    public void setManager(Manager manager) {
        this.manager = manager;
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }

    public byte[] getImg() {
        return img;
    }

    public void setImg(byte[] img) {
        this.img = img;
    }

    public AdminImageDto getImageDto(){
        AdminImageDto adminImageDto = new AdminImageDto();
        adminImageDto.setId(admin.getId());
        adminImageDto.setByteImage(img);
        adminImageDto.setFullName(admin.getFullName());
        adminImageDto.setEmail(admin.getEmail());
        adminImageDto.setGender(admin.getGender());
        adminImageDto.setPhone(admin.getPhone());
        adminImageDto.setyOfBirth(admin.getyOfBirth());
        adminImageDto.setAddress(admin.getAddress());
        adminImageDto.setStatus(admin.isActive());
        return adminImageDto;
    }


}

