package com.Shop.entity;

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
        adminImageDto.setByteImage(img);
        return adminImageDto;
    }
}

