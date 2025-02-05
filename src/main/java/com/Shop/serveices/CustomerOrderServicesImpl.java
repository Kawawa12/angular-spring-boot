package com.Shop.serveices;

import com.Shop.dto.CustomerOrderDto;
import com.Shop.dto.OrderItemDto;
import com.Shop.entity.*;
import com.Shop.repo.CustomerOrderRepo;
import com.Shop.repo.OrderItemsRepo;
import com.Shop.repo.ProductRepo;
import com.Shop.repo.UserRepository;
import com.Shop.response.CartItems;
import com.Shop.response.CustomerOrderRespDto;
import com.Shop.response.Response;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

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
            response.setMessage("User is not found.");
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


    //Method to get all customer orders
    @Override
    public List<CustomerOrderRespDto> getAllCustomerOrders(){
        //Fetch the customer order list
        List<CustomerOrder> customerOrders = customerOrderRepo.findAll();

        return customerOrders.stream().map(order -> {
              //Create new instance of Customer order
            CustomerOrderRespDto respDto = new CustomerOrderRespDto();
            respDto.setId(order.getId());
            respDto.setUserId(order.getCustomer().getId());
            respDto.setCustomerName(order.getCustomerName());
            respDto.setOrderDate(order.getOrderedAt());
            respDto.setOrderStatus(order.getStatus().toString());

            //Map the List items first then set it
            List<CartItems> cartItems = order.getOrderItems().stream().map(item-> {
                CartItems cartItem = new CartItems();
                cartItem.setId(item.getId());
                cartItem.setItemName(item.getItemName());
                cartItem.setQuantity(item.getQuant());
                cartItem.setPricePerProduct(item.getPricePerProduct());
                cartItem.setTotalPrice(item.getQuant() * item.getPricePerProduct()); //calculate total price
                return cartItem;
            }).collect(Collectors.toList());

            respDto.setCartItems(cartItems);
            //calculate the total amount for customer order and set
            double totalAmount = cartItems.stream().mapToDouble(CartItems::getTotalPrice).sum();
            respDto.setTotalAmount(totalAmount);

            return respDto;
        }).toList();
    }

    public List<CustomerOrderRespDto> getAllNewOrders(){
        //Fetch the customer order list
        List<CustomerOrder> customerOrders = customerOrderRepo.findAll()
                .stream().filter(order ->
                        order.getStatus().equals(OrderStatus.PENDING)).toList();

        return customerOrders.stream().map(order -> {
            //Create new instance of Customer order
            CustomerOrderRespDto respDto = new CustomerOrderRespDto();
            respDto.setId(order.getId());
            respDto.setUserId(order.getCustomer().getId());
            respDto.setCustomerName(order.getCustomerName());
            respDto.setOrderDate(order.getOrderedAt());
            respDto.setOrderStatus(order.getStatus().toString());

            //Map the List items first then set it
            List<CartItems> cartItems = order.getOrderItems().stream().map(item-> {
                CartItems cartItem = new CartItems();
                cartItem.setId(item.getId());
                cartItem.setItemName(item.getItemName());
                cartItem.setQuantity(item.getQuant());
                cartItem.setPricePerProduct(item.getPricePerProduct());
                cartItem.setTotalPrice(item.getQuant() * item.getPricePerProduct()); //calculate total price
                return cartItem;
            }).collect(Collectors.toList());

            respDto.setCartItems(cartItems);
            //calculate the total amount for customer order and set
            double totalAmount = cartItems.stream().mapToDouble(CartItems::getTotalPrice).sum();
            respDto.setTotalAmount(totalAmount);

            return respDto;
        }).toList();
    }

    public List<CustomerOrderRespDto> getAllConfirmedOrders(){
        //Fetch the customer order list
        List<CustomerOrder> customerOrders = customerOrderRepo.findAll()
                .stream().filter(order ->
                        order.getStatus().equals(OrderStatus.CONFIRMED)).toList();

        return customerOrders.stream().map(order -> {
            //Create new instance of Customer order
            CustomerOrderRespDto respDto = new CustomerOrderRespDto();
            respDto.setId(order.getId());
            respDto.setUserId(order.getCustomer().getId());
            respDto.setCustomerName(order.getCustomerName());
            respDto.setOrderDate(order.getOrderedAt());
            respDto.setOrderStatus(order.getStatus().toString());

            //Map the List items first then set it
            List<CartItems> cartItems = order.getOrderItems().stream().map(item-> {
                CartItems cartItem = new CartItems();
                cartItem.setId(item.getId());
                cartItem.setItemName(item.getItemName());
                cartItem.setQuantity(item.getQuant());
                cartItem.setPricePerProduct(item.getPricePerProduct());
                cartItem.setTotalPrice(item.getQuant() * item.getPricePerProduct()); //calculate total price
                return cartItem;
            }).collect(Collectors.toList());

            respDto.setCartItems(cartItems);
            //calculate the total amount for customer order and set
            double totalAmount = cartItems.stream().mapToDouble(CartItems::getTotalPrice).sum();
            respDto.setTotalAmount(totalAmount);

            return respDto;
        }).toList();
    }

    public List<CustomerOrderRespDto> getAllCompletedOrders(){
        //Fetch the customer order list
        List<CustomerOrder> customerOrders = customerOrderRepo.findAll()
                .stream().filter(order ->
                        order.getStatus().equals(OrderStatus.COMPLETED)).toList();

        return customerOrders.stream().map(order -> {
            //Create new instance of Customer order
            CustomerOrderRespDto respDto = new CustomerOrderRespDto();
            respDto.setId(order.getId());
            respDto.setUserId(order.getCustomer().getId());
            respDto.setCustomerName(order.getCustomerName());
            respDto.setOrderDate(order.getOrderedAt());
            respDto.setOrderStatus(order.getStatus().toString());

            //Map the List items first then set it
            List<CartItems> cartItems = order.getOrderItems().stream().map(item-> {
                CartItems cartItem = new CartItems();
                cartItem.setId(item.getId());
                cartItem.setItemName(item.getItemName());
                cartItem.setQuantity(item.getQuant());
                cartItem.setPricePerProduct(item.getPricePerProduct());
                cartItem.setTotalPrice(item.getQuant() * item.getPricePerProduct()); //calculate total price
                return cartItem;
            }).collect(Collectors.toList());

            respDto.setCartItems(cartItems);
            //calculate the total amount for customer order and set
            double totalAmount = cartItems.stream().mapToDouble(CartItems::getTotalPrice).sum();
            respDto.setTotalAmount(totalAmount);

            return respDto;
        }).toList();
    }

    public List<CustomerOrderRespDto> getAllCanceledOrders(){
        //Fetch the customer order list
        List<CustomerOrder> customerOrders = customerOrderRepo.findAll()
                .stream().filter(order -> order.getStatus().equals(OrderStatus.CANCELED)).toList();

        return customerOrders.stream().map(order -> {
            //Create new instance of Customer order
            CustomerOrderRespDto respDto = new CustomerOrderRespDto();
            respDto.setId(order.getId());
            respDto.setUserId(order.getCustomer().getId());
            respDto.setCustomerName(order.getCustomerName());
            respDto.setOrderDate(order.getOrderedAt());
            respDto.setOrderStatus(order.getStatus().toString());

            //Map the List items first then set it
            List<CartItems> cartItems = order.getOrderItems().stream().map(item-> {
                CartItems cartItem = new CartItems();
                cartItem.setId(item.getId());
                cartItem.setItemName(item.getItemName());
                cartItem.setQuantity(item.getQuant());
                cartItem.setPricePerProduct(item.getPricePerProduct());
                cartItem.setTotalPrice(item.getQuant() * item.getPricePerProduct()); //calculate total price
                return cartItem;
            }).collect(Collectors.toList());

            respDto.setCartItems(cartItems);
            //calculate the total amount for customer order and set
            double totalAmount = cartItems.stream().mapToDouble(CartItems::getTotalPrice).sum();
            respDto.setTotalAmount(totalAmount);

            return respDto;
        }).toList();
    }

    @Override
    public String confirmOrder(Long customerId,Long orderId) {
        //find order if exist
        Optional<AppUser> customerOpt = userRepository.findById(customerId);
        Optional<CustomerOrder> order = customerOrderRepo.findById(orderId);
        if(order.isPresent() && customerOpt.isPresent()) {
            CustomerOrder existingOrder = order.get();
            if(existingOrder.getStatus().equals(OrderStatus.PENDING)){
            existingOrder.setStatus(OrderStatus.CONFIRMED);
            existingOrder.setCustomer(customerOpt.get());
            customerOrderRepo.save(existingOrder);
            }else if (existingOrder.getStatus().equals(OrderStatus.CANCELED)) {
                return "Sorry!,canceled order can not be confirmed.";
            } else {
                return "Sorry!, Order has already confirmed";
            }
        }else{
            return "Order not found.";
        }
        return "Order Confirmed Successful.";
    }

    @Override
    public String completeOrder(Long customerId,Long orderId) {
        //find order if exist
        Optional<AppUser> customerOpt = userRepository.findById(customerId);
        Optional<CustomerOrder> order = customerOrderRepo.findById(orderId);
        if(order.isPresent() && customerOpt.isPresent()) {
            CustomerOrder existingOrder = order.get();
            if(existingOrder.getStatus().equals(OrderStatus.CONFIRMED)){
                existingOrder.setStatus(OrderStatus.COMPLETED);
                existingOrder.setCustomer(customerOpt.get());
                customerOrderRepo.save(existingOrder);
            } else if (existingOrder.getStatus().equals(OrderStatus.PENDING)) {
                return "Order is not confirmed! Please confirm the Order to continue.";
            } else if (existingOrder.getStatus().equals(OrderStatus.CANCELED)) {
                return "Sorry!, canceled order can not mark as complete.";
            } else {
                return "Order already marked as complete.";
            }
        }else{
            return "Order not found.";
        }
        return "Order Successful marked as complete.";
    }

    @Override
    public String cancelOrder(Long customerId,Long orderId) {
        //find order if exist
        Optional<AppUser> customerOpt = userRepository.findById(customerId);
        Optional<CustomerOrder> order = customerOrderRepo.findById(orderId);
        if(order.isPresent() && customerOpt.isPresent()) {
            CustomerOrder existingOrder = order.get();
            if(existingOrder.getStatus().equals(OrderStatus.PENDING) || existingOrder.getStatus().equals(OrderStatus.CONFIRMED)){
                existingOrder.setStatus(OrderStatus.CANCELED);
                existingOrder.setCustomer(customerOpt.get());
                customerOrderRepo.save(existingOrder);
            }else if (existingOrder.getStatus().equals(OrderStatus.COMPLETED)) {
                return "OSorry!, completed Order can not be canceled!";
            } else {
                return "Order Already has been canceled!";
            }
        }else{
            return "Order not found.";
        }
        return "Order canceled successful.";
    }


}
