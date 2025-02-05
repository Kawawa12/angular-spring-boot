package com.Shop.serveices;


import com.Shop.dto.ProductDto;
import com.Shop.entity.Category;
import com.Shop.entity.Product;
import com.Shop.entity.Stock;
import com.Shop.repo.CategoryRepo;
import com.Shop.repo.ProductRepo;
import com.Shop.repo.StockRepository;
import com.Shop.response.Response;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepo productRepo;
    private final CategoryRepo categoryRepo;
    private final StockRepository stockRepository;

    @Autowired
    public ProductServiceImpl(ProductRepo productRepo, CategoryRepo categoryRepo, StockRepository stockRepository) {
        this.productRepo = productRepo;
        this.categoryRepo = categoryRepo;
        this.stockRepository = stockRepository;
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
        product.setDescription(productDto.getDesc());
        product.setActive(true);
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

        //Create Stock instance
        Stock stock = new Stock();
        stock.setStockQuantity(productDto.getStock());
        stock.setCreatedAt(LocalDateTime.now());
        stock.setUpdatedAt(LocalDateTime.now());
        stock.setProduct(savedProduct);
        //Save the stock
        stockRepository.save(stock);

        //Set stock to product and update product table with stock
        product.setStock(stock);
        productRepo.save(product);

        // Return ProductDto
        return savedProduct.getProductDto();
    }


    @Override
    @Transactional
    public List<ProductDto> getAllProducts() {
        List<Product> products = productRepo.findAll();
        return products.stream().map(Product::getProductDto).collect(Collectors.toList());
    }

    @Override
    public Response activateProduct(Long id) {
        Response response = new Response();
       try {
           Optional<Product> optionalProduct = productRepo.findById(id);
           if(optionalProduct.isPresent() && !optionalProduct.get().isActive()){
               Product product = optionalProduct.get();
               product.setActive(true);
               productRepo.save(product);
               response.setMessage("Product activated successful.");
               response.setStatus(200);
           } else if (optionalProduct.isEmpty()) {
               response.setStatus(402);
               response.setMessage("Product is not found.");
           } else {
               response.setMessage("Sorry! the product is still active.");
           }
       }catch (Exception e){
           response.setMessage("Error! product not found, " + e.getMessage());
           response.setStatus(403);
       }
        return response;
    }

    @Override
    public Response deActivateProduct(Long id) {
        Response response = new Response();
        try {
            Optional<Product> optionalProduct = productRepo.findById(id);
            if(optionalProduct.isPresent() && optionalProduct.get().isActive()){
                Product product = optionalProduct.get();
                product.setActive(false);
                productRepo.save(product);
                response.setMessage("Product Deactivated successful.");
                response.setStatus(200);
            }else if (optionalProduct.isEmpty()) {
                response.setStatus(402);
                response.setMessage("Product is not found.");
            }
            else {
                response.setMessage("Sorry! Product is still not active.");
            }
        }catch (Exception e){
            response.setMessage("Error! product not found, " + e.getMessage());
            response.setStatus(403);
        }
        return response;
    }

    @Override
    public List<ProductDto> getAllActiveProducts() {
        List<Product> products = productRepo.findAll();
        return  products.stream().filter(Product::isActive).map(Product::getProductDto).toList();
    }

    @Override
    public List<ProductDto> getAllDeactivatedProducts() {
        List<Product> products = productRepo.findAll();
        return  products.stream().filter(product -> !product.isActive())
                .map(Product::getProductDto).toList();
    }


}

