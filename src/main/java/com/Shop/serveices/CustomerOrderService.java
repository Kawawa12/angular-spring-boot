package com.Shop.serveices;

import com.Shop.dto.CustomerOrderDto;
import com.Shop.response.Response;

public interface CustomerOrderService {

    Response createOrder(CustomerOrderDto customerOrderDto, Long id);
}
