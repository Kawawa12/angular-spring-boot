package com.Shop.entity;

import com.Shop.dto.ProductDto;
import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;



@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private double price;

    @Column(nullable = true)
    private String description;

    @Lob
    private byte[] img;

    @Column(nullable = true)
    private Integer stockQuantity;


    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Category category;

    public Product() {
    }

    public Product(Long id, String name, double price, String description, byte[] img, Integer stockQuantity, Category category) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
        this.img = img;
        this.stockQuantity = stockQuantity;
        this.category = category;
    }

    public Long getId() {
        return id;
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public byte[] getImg() {
        return img;
    }

    public void setImg(byte[] img) {
        this.img = img;
    }

    public Integer getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(Integer stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public ProductDto getProductDto() {
        ProductDto productDto = new ProductDto();
        productDto.setId(id);
        productDto.setName(name);
        productDto.setDesc(description);
        productDto.setPrice(price);
        productDto.setStock(stockQuantity);
        productDto.setByteImage(img);
        productDto.setCategoryId(category.getId());
        productDto.setCatName(productDto.getName());
        return productDto;
    }

}
