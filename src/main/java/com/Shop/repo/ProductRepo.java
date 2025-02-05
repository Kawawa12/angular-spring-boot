package com.Shop.repo;

import com.Shop.dto.ProductDto;
import com.Shop.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepo extends JpaRepository<Product, Long> {

}
