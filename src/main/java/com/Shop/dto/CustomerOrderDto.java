package com.Shop.dto;

import java.util.List;

public class CustomerOrderDto {
    //Contains list of items/products
    List<OrderItemDto> orderItems;

    private double totalAmount;

    public CustomerOrderDto() {
    }

    public CustomerOrderDto(List<OrderItemDto> orderItems, double totalAmount) {
        this.orderItems = orderItems;
        this.totalAmount = totalAmount;
    }

    public List<OrderItemDto> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItemDto> orderItems) {
        this.orderItems = orderItems;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }
}
