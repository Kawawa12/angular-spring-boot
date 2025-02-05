package com.Shop.response;

import java.time.LocalDateTime;

public class StockResponseDto {

    private Long id;

    private String productName;

    private int stockQuantity;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public StockResponseDto() {
    }

    public StockResponseDto(Long id,String productName, LocalDateTime updatedAt, LocalDateTime createdAt, int stockQuantity) {
        this.id = id;
        this.productName = productName;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.stockQuantity = stockQuantity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
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

    public int getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(int stockQuantity) {
        this.stockQuantity = stockQuantity;
    }
}
