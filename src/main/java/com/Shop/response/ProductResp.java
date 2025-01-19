package com.Shop.response;

public class ProductResp {

    private Long id;

    private String name;

    private String categoryName;

    private double price;

    private String imageUrl;

    public ProductResp() {
        super();
    }

    public ProductResp(Long id, double price, String categoryName, String name, String imageUrl) {
        this.id = id;
        this.price = price;
        this.categoryName = categoryName;
        this.name = name;
        this.imageUrl = imageUrl;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
