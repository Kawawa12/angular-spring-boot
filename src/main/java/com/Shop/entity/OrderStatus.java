package com.Shop.entity;

public enum OrderStatus {
    PENDING,
    CONFIRMED,
    COMPLETED,
    CANCELED;

    @Override
    public String toString(){
        String name = name();
        //Capitalize the first character
        return name.charAt(0) + name.substring(1).toLowerCase();
    }
}
