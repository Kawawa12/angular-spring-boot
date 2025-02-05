package com.Shop.serveices;

import com.Shop.dto.CustomerOrderDto;
import com.Shop.response.CustomerOrderRespDto;
import com.Shop.response.Response;

import java.util.List;

public interface CustomerOrderService {

    Response createOrder(CustomerOrderDto customerOrderDto, Long id);
    List<CustomerOrderRespDto> getAllCustomerOrders();
    String confirmOrder(Long customerId,Long orderId);
    String completeOrder(Long customerId,Long orderId);
    String cancelOrder(Long customerId,Long orderId);
    List<CustomerOrderRespDto> getAllNewOrders();
    List<CustomerOrderRespDto> getAllConfirmedOrders();
    List<CustomerOrderRespDto> getAllCompletedOrders();
    List<CustomerOrderRespDto> getAllCanceledOrders();

}
