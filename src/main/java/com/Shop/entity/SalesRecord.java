package com.Shop.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
public class SalesRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate saleDate;

    @ElementCollection
    List<String> productNames = new ArrayList<>();

    @ElementCollection
    List<Integer> quantities = new ArrayList<>();

    @ElementCollection
    List<Double> prices = new ArrayList<>();

    @ElementCollection
    List<String> descriptions = new ArrayList<>();

    private double totalAmount; // Total amount for the day

    public SalesRecord() {
    }

    public void addSales(String productName, int quantity, double price, String description) {
        this.productNames.add(productName);
        this.quantities.add(quantity);
        this.prices.add(price);
        this.descriptions.add(description);
        this.totalAmount += (price * quantity);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public List<String> getDescriptions() {
        return descriptions;
    }

    public void setDescriptions(List<String> descriptions) {
        this.descriptions = descriptions;
    }

    public List<Double> getPrices() {
        return prices;
    }

    public void setPrices(List<Double> prices) {
        this.prices = prices;
    }

    public List<Integer> getQuantities() {
        return quantities;
    }

    public void setQuantities(List<Integer> quantities) {
        this.quantities = quantities;
    }

    public List<String> getProductNames() {
        return productNames;
    }

    public void setProductNames(List<String> productNames) {
        this.productNames = productNames;
    }

    public LocalDate getSaleDate() {
        return saleDate;
    }

    public void setSaleDate(LocalDate saleDate) {
        this.saleDate = saleDate;
    }

    @Override
    public String toString() {
        return "SalesRecord{" +
                "id=" + id +
                ", saleDate=" + saleDate +
                ", productNames=" + productNames +
                ", quantities=" + quantities +
                ", prices=" + prices +
                ", descriptions=" + descriptions +
                ", totalAmount=" + totalAmount +
                '}';
    }
}