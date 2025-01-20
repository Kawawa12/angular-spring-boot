package com.Shop.entity;

import com.Shop.response.CategoryDto;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "category")
    private List<Product> products;

    public Category() {
        super();
    }

    public Category(Long id, List<Product> products, String name) {
        this.id = id;
        this.products = products;
        this.name = name;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
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

    public CategoryDto getCatDto() {
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setCategoryId(id);
        categoryDto.setCategoryName(name);

        return categoryDto;
    }

}
