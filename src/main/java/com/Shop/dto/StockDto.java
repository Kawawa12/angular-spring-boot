package com.Shop.dto;

import java.time.LocalDateTime;

public class StockDto {

    private Long id;

    private String prodName;

    private Integer stockValue;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public StockDto() {
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

    public Integer getStockValue() {
        return stockValue;
    }

    public void setStockValue(Integer stockValue) {
        this.stockValue = stockValue;
    }

    public String getProdName() {
        return prodName;
    }

    public void setProdName(String prodName) {
        this.prodName = prodName;
    }
}
