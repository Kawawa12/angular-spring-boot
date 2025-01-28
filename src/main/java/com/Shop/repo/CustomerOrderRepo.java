package com.Shop.repo;

import com.Shop.entity.CustomerOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerOrderRepo extends JpaRepository<CustomerOrder, Long> {
}
