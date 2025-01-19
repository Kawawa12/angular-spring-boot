package com.Shop.dto;

public class ProductDto {

    private String name;

    private Long catId;

    private  double price;

    public ProductDto() {
        super();
    }

    public ProductDto(String name, Long catId, double price) {
        this.name = name;
        this.catId = catId;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCatId(Long catId) {
        this.catId = catId;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Long getCatId() {
        return catId;
    }


    public double getPrice() {
        return price;
    }

}
