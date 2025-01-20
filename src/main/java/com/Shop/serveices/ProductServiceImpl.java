package com.Shop.serveices;


import com.Shop.dto.ProductDto;
import com.Shop.entity.Category;
import com.Shop.entity.Product;
import com.Shop.repo.CategoryRepo;
import com.Shop.repo.ProductRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepo productRepo;
    private final CategoryRepo categoryRepo;

    @Autowired
    public ProductServiceImpl(ProductRepo productRepo, CategoryRepo categoryRepo) {
        this.productRepo = productRepo;
        this.categoryRepo = categoryRepo;
    }


    @Override
    @Transactional
    public ProductDto addProduct(ProductDto productDto) {
        // Convert byte array from DTO to image byte[] for the Product entity
        byte[] imageBytes = productDto.getByteImage();

        // Create Product entity and set values
        Product product = new Product();
        product.setName(productDto.getName());
        product.setPrice(productDto.getPrice());
        product.setImg(imageBytes); // Set the byte array image

        // Validate categoryId
        if (productDto.getCategoryId() == null) {
            throw new IllegalArgumentException("Category ID must not be null");
        }

        // Fetch category and set it
        Category category = categoryRepo.findById(productDto.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        product.setCategory(category);

        // Save Product and return DTO
        Product savedProduct = productRepo.save(product);

        // Return ProductDto
        return savedProduct.getProductDto();
    }



    @Override
    @Transactional
    public List<ProductDto> getAllProducts() {
        List<Product> products = productRepo.findAll();
        return products.stream().map(Product::getProductDto).collect(Collectors.toList());
    }
}

