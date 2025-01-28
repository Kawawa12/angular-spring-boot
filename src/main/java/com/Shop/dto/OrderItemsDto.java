package com.Shop.dto;


import lombok.Data;

@Data
public class OrderItemsDto {

    private Long id;

    private String productName;

    private int quant;

    private double pricePerProd;

    private double totalPrice;

}
