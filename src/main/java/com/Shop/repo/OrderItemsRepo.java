package com.Shop.repo;

import com.Shop.entity.OrderItems;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemsRepo extends JpaRepository<OrderItems, Long> {

}
