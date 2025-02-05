package com.Shop.dto;

public class ConfirmOrderDto {
    private Long customerId;

    private Long orderId;

    public ConfirmOrderDto() {
    }

    public Long getCustomerId() {
        return customerId;
    }

    public Long getOrderId() {
        return orderId;
    }
}
