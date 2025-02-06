package com.Shop.controller;

import com.Shop.dto.AdminImageDto;
import com.Shop.dto.ConfirmOrderDto;
import com.Shop.dto.ProductDto;
import com.Shop.dto.StockData;
import com.Shop.response.*;
import com.Shop.serveices.*;
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
    private final StockService stockService;
    private final ManagerService managerService;

    public AdminController(CategoryService categoryService, ProductService productService,
                           CustomerOrderService customerOrderService, StockService stockService, ManagerService managerService) {
        this.categoryService = categoryService;
        this.productService = productService;
        this.customerOrderService = customerOrderService;
        this.stockService = stockService;
        this.managerService = managerService;
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

    @GetMapping("/admin/{adminId}")
    public ResponseEntity<AdminRespDto> getAdmin(@PathVariable Long adminId) {
        return ResponseEntity.ok(managerService.getAdmin(adminId));
    }


    @GetMapping("/products")
    public ResponseEntity<List<ProductDto>> getAllProducts(){
        List<ProductDto> productDto = productService.getAllProducts();
        return ResponseEntity.ok(productDto);
    }

    @GetMapping("/orders")
    public ResponseEntity<List<CustomerOrderRespDto>> getAllOrders() {
        return ResponseEntity.ok(customerOrderService.getAllCustomerOrders());
    }

    @GetMapping("/view-stock")
    public ResponseEntity<List<StockResponseDto>> getAllStockProducts() {
        return ResponseEntity.ok(stockService.viewProductsStock());
    }

    @PostMapping("/add-stock")
    public ResponseEntity<?> addStock(@RequestBody StockData stockData) {
        try {
            String message = stockService.addStockToProduct(stockData.getStockValue(), stockData.getProductId());
            return ResponseEntity.ok(message);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getMessage());
        }
    }

    @PostMapping("/confirm-order")
    public ResponseEntity<?> confirmOrder(@RequestBody ConfirmOrderDto data){
        return ResponseEntity.ok(customerOrderService.confirmOrder(data.getCustomerId(), data.getOrderId()));
    }

    @PostMapping("/cancel-order")
    public ResponseEntity<?> cancelOrder(@RequestBody ConfirmOrderDto data){
        return ResponseEntity.ok(customerOrderService.cancelOrder(data.getCustomerId(), data.getOrderId()));
    }

    @PostMapping("/complete-order")
    public ResponseEntity<?> completeOrder(@RequestBody ConfirmOrderDto data){
        return ResponseEntity.ok(customerOrderService.completeOrder(data.getCustomerId(), data.getOrderId()));
    }

    @GetMapping("/new-orders")
    public ResponseEntity<List<CustomerOrderRespDto>> newOrders() {
        return ResponseEntity.ok(customerOrderService.getAllNewOrders());
    }

    @GetMapping("/confirmed-orders")
    public ResponseEntity<List<CustomerOrderRespDto>> confirmedOrders() {
        return ResponseEntity.ok(customerOrderService.getAllConfirmedOrders());
    }

    @GetMapping("/completed-orders")
    public ResponseEntity<List<CustomerOrderRespDto>> completedOrders() {
        return ResponseEntity.ok(customerOrderService.getAllCompletedOrders());
    }

    @GetMapping("/canceled-orders")
    public ResponseEntity<List<CustomerOrderRespDto>> canceledOrders() {
        return ResponseEntity.ok(customerOrderService.getAllCanceledOrders());
    }

    @PostMapping("/activate-product/{id}")
    public ResponseEntity<Response> activateProduct(@PathVariable Long id){
        return ResponseEntity.ok(productService.activateProduct(id));
    }

    @PostMapping("/deactivate-product/{id}")
    public ResponseEntity<Response> deActivateProduct(@PathVariable Long id){
        return ResponseEntity.ok(productService.deActivateProduct(id));
    }

    @GetMapping("/deactivated-products")
    public ResponseEntity<List<ProductDto>> getAllDeactivatedProduct(){
        return ResponseEntity.ok(productService.getAllDeactivatedProducts());
    }

    @GetMapping("/active-products")
    public ResponseEntity<List<ProductDto>> getAllActiveProduct(){
        return ResponseEntity.ok(productService.getAllActiveProducts());
    }

    @GetMapping("/admin-img/{id}")
    public ResponseEntity<String> getAdminImg(@PathVariable Long id) {
        return ResponseEntity.ok(managerService.getAdminImage(id));
    }

    @PutMapping("/update-admin-img/{id}")
    public ResponseEntity<String> updateAdminImg(@PathVariable Long id,
                                                 @RequestParam("image")MultipartFile imgFile)throws IOException{
        return ResponseEntity.ok(managerService.updateAdminImg(id,imgFile));
    }

}
