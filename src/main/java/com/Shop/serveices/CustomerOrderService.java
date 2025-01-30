package com.Shop.serveices;

import com.Shop.dto.CustomerOrderDto;
import com.Shop.response.CustomerOrderRespDto;
import com.Shop.response.Response;

import java.util.List;

public interface CustomerOrderService {

    Response createOrder(CustomerOrderDto customerOrderDto, String email);
    List<CustomerOrderRespDto> getAllCustomerOrders();
}
