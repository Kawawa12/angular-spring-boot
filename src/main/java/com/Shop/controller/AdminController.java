package com.Shop.controller;

import com.Shop.dto.ProductDto;
import com.Shop.response.CategoryDto;
import com.Shop.response.CustomerOrderRespDto;
import com.Shop.response.Response;
import com.Shop.serveices.CategoryService;
import com.Shop.serveices.CustomerOrderService;
import com.Shop.serveices.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin("http//:localhost:4200")
@RequestMapping("/auth/api")
public class AdminController {

    private final CategoryService categoryService;
    private final ProductService productService;
    private final CustomerOrderService customerOrderService;

    public AdminController(CategoryService categoryService, ProductService productService, CustomerOrderService customerOrderService) {
        this.categoryService = categoryService;
        this.productService = productService;
        this.customerOrderService = customerOrderService;
    }

    @PostMapping("/add-category")
    public ResponseEntity<Response> addCategory(@RequestParam String name) {
        return ResponseEntity.ok(categoryService.addCategory(name));
    }

    @GetMapping("/categories")
    public ResponseEntity<List<CategoryDto>> getAllCategory(){
        return ResponseEntity.ok(categoryService.getAll());
    }

    @PostMapping("/add-product")
    public ResponseEntity<ProductDto> addProduct(
            @RequestParam("categoryId") Long categoryId,
            @RequestParam("name") String name,
            @RequestParam("description") String description,
            @RequestParam("stock") int stock,
            @RequestParam("price") double price,
            @RequestParam("image") MultipartFile image) throws IOException {

        System.out.println("Name: " + name);
        // Check if the image is not null
        byte[] imageBytes = null;
        if (image != null && !image.isEmpty()) {
            imageBytes = image.getBytes(); // Convert MultipartFile to byte[]
        }

        // Create ProductDto with received values
        ProductDto productDto = new ProductDto();
        productDto.setCategoryId(categoryId);
        productDto.setName(name);
        productDto.setPrice(price);
        productDto.setStock(stock);
        productDto.setDesc(description);
        productDto.setByteImage(imageBytes); // Pass byte array image

        // Call service to save product
        ProductDto savedProduct = productService.addProduct(productDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedProduct);
    }



    @GetMapping("products")
    public ResponseEntity<List<ProductDto>> getAllProducts(){
        List<ProductDto> productDto = productService.getAllProducts();
        return ResponseEntity.ok(productDto);
    }

    @GetMapping("/orders")
    public ResponseEntity<List<CustomerOrderRespDto>> getAllOrders() {
        return ResponseEntity.ok(customerOrderService.getAllCustomerOrders());
    }

}
