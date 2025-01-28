package com.Shop.dto;

public class ProductDto {

    private Long id;
    private String name;
    private String desc;
    private int stock;
    private Long categoryId;
    private double price;
    private byte[] byteImage; // Store image as byte[]
    private String catName;

    public ProductDto() {
        super();
    }

    public ProductDto(Long id, String name, String desc, int stock, Long categoryId, double price, byte[] byteImage, String catName) {
        this.id = id;
        this.name = name;
        this.desc = desc;
        this.stock = stock;
        this.categoryId = categoryId;
        this.price = price;
        this.byteImage = byteImage;
        this.catName = catName;
    }

    public Long getId() {
        return id;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
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
