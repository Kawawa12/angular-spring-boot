package com.Shop.dto;


import lombok.Data;

import java.time.LocalDateTime;

@Data
public class StockDtoResp {

    private Long id;

    private String productName;

    private int stockQuant;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public StockDtoResp() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public int getStockQuant() {
        return stockQuant;
    }

    public void setStockQuant(int stockQuant) {
        this.stockQuant = stockQuant;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }
}
