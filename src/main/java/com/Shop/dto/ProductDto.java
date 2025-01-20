package com.Shop.dto;

public class ProductDto {

    private Long id;
    private String name;
    private Long categoryId;
    private double price;
    private byte[] byteImage; // Store image as byte[]
    private String catName;

    public ProductDto() {
        super();
    }

    public ProductDto(Long id, String name, Long categoryId, double price, byte[] byteImage, String catName) {
        this.id = id;
        this.name = name;
        this.categoryId = categoryId;
        this.price = price;
        this.byteImage = byteImage;
        this.catName = catName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public byte[] getByteImage() {
        return byteImage;
    }

    public void setByteImage(byte[] byteImage) {
        this.byteImage = byteImage;
    }

    public String getCatName() {
        return catName;
    }

    public void setCatName(String catName) {
        this.catName = catName;
    }
}
