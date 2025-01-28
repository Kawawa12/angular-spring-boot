package com.Shop.entity;

import jakarta.persistence.*;

@Entity
public class OrderItems {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String itemName;

    private int quant;

    private double pricePerProduct;

    private double totalPrice;

//    @OneToMany(mappedBy = "orderItems")
//    private List<Product> products;

    @ManyToOne(optional = false)
    @JoinColumn(name = "customer_order_id", nullable = false)
    private CustomerOrder customerOrder;

    public OrderItems() {
    }

    public OrderItems(CustomerOrder customerOrder, double totalPrice, String itemName, Long id, int quant, double pricePerProduct) {
        this.customerOrder = customerOrder;
        this.totalPrice = totalPrice;
        this.itemName = itemName;
        this.id = id;
        this.quant = quant;
        this.pricePerProduct = pricePerProduct;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CustomerOrder getCustomerOrder() {
        return customerOrder;
    }

    public void setCustomerOrder(CustomerOrder customerOrder) {
        this.customerOrder = customerOrder;
    }

    public double getPricePerProduct() {
        return pricePerProduct;
    }

    public void setPricePerProduct(double pricePerProduct) {
        this.pricePerProduct = pricePerProduct;
    }

    public int getQuant() {
        return quant;
    }

    public void setQuant(int quant) {
        this.quant = quant;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }


}
