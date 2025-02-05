package com.Shop.repo;

import com.Shop.entity.Product;
import com.Shop.entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StockRepository extends JpaRepository<Stock, Long> {

    Optional<Stock> findByProductId(Long id);
    Stock findByProduct(Product product);
}
