package com.Shop.serveices;

import com.Shop.dto.CustomerOrderDto;
import com.Shop.dto.OrderItemDto;
import com.Shop.entity.*;
import com.Shop.repo.CustomerOrderRepo;
import com.Shop.repo.OrderItemsRepo;
import com.Shop.repo.ProductRepo;
import com.Shop.repo.UserRepository;
import com.Shop.response.Response;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerOrderServicesImpl implements CustomerOrderService {

    private final CustomerOrderRepo customerOrderRepo;
    private final OrderItemsRepo orderItemsRepo;
    private final ProductRepo productRepo;
    private final UserRepository userRepository;

    public CustomerOrderServicesImpl(CustomerOrderRepo customerOrderRepo, OrderItemsRepo orderItemsRepo, ProductRepo productRepo, UserRepository userRepository) {
        this.customerOrderRepo = customerOrderRepo;
        this.orderItemsRepo = orderItemsRepo;
        this.productRepo = productRepo;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public Response createOrder(CustomerOrderDto customerOrderDto, Long id) {
        Response response = new Response();

        // Find customer if exists
        Optional<AppUser> customer = userRepository.findById(id);

        if (customer.isPresent()) {
            // Create customer order
            CustomerOrder customerOrder = new CustomerOrder();
            customerOrder.setCustomerName(customer.get().getFullName());
            customerOrder.setCustomer(customer.get());
            customerOrder.setOrderedAt(LocalDateTime.now());
            customerOrder.setStatus(OrderStatus.PENDING);
            customerOrder.setTotalAmount(customerOrderDto.getTotalAmount());

            // Save the customer order first
            customerOrder = customerOrderRepo.save(customerOrder); // This saves and flushes the entity

            // Map and save order items
            CustomerOrder finalCustomerOrder = customerOrder;
            List<OrderItems> orderItems = customerOrderDto.getOrderItems()
                    .stream()
                    .map(itemDto -> mapToOrderItems(itemDto, finalCustomerOrder))
                            .toList();


            // No need to explicitly save orderItems here as it's already being saved in mapToOrderItems method.
            response.setStatus(200);
            response.setMessage("Order saved successfully.");
        } else {
            response.setStatus(403);
            response.setMessage("Customer not found.");
        }
        return response;
    }

    private OrderItems mapToOrderItems(OrderItemDto itemDto, CustomerOrder customerOrder) {
        OrderItems orderItems = new OrderItems();

        // Find the product by ID
        Product product = productRepo.findById(itemDto.getId())
                .orElseThrow(() -> new RuntimeException("Product not found")); // Handle missing product case

        orderItems.setItemName(product.getName());
        orderItems.setQuant(itemDto.getQuantity());
        orderItems.setPricePerProduct(itemDto.getPrice());
        orderItems.setTotalPrice(itemDto.getTotalPrice());
        orderItems.setCustomerOrder(customerOrder);

        // Save the order items
        orderItemsRepo.save(orderItems);

        return orderItems;
    }
}
