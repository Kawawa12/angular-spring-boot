package com.Shop.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ManagerProfileDto {

    private String fullName;
    private String email;
    private String phone;
    private String address;
    private String gender;
    private String yOfBirth;

    public ManagerProfileDto() {
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getGender() {
        return gender;
    }

    public String getyOfBirth() {
        return yOfBirth;
    }

    public void setyOfBirth(String yOfBirth) {
        this.yOfBirth = yOfBirth;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
