package com.Shop.repo;

import com.Shop.entity.CustomerOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CustomerOrderRepo extends JpaRepository<CustomerOrder, Long> {

    @Query("SELECT COUNT(c) FROM CustomerOrder c WHERE c.status = 'PENDING'")
    int countPendingOrders();



}
