package com.Shop.controller;

import com.Shop.dto.*;
import com.Shop.entity.SalesRecord;
import com.Shop.repo.SalesRepository;
import com.Shop.response.*;
import com.Shop.serveices.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("http//:localhost:4200")
@RequestMapping("/auth/api")
public class AdminController {

    private final CategoryService categoryService;
    private final ProductService productService;
    private final CustomerOrderService customerOrderService;
    private final StockService stockService;
    private final ManagerService managerService;
    private final SalesServices salesServices;
    private final SalesRepository salesRepository;

    public AdminController(CategoryService categoryService, ProductService productService,
                           CustomerOrderService customerOrderService, StockService stockService,
                           ManagerService managerService, SalesServices salesServices, SalesRepository salesRepository) {
        this.categoryService = categoryService;
        this.productService = productService;
        this.customerOrderService = customerOrderService;
        this.stockService = stockService;
        this.managerService = managerService;
        this.salesServices = salesServices;
        this.salesRepository = salesRepository;
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

    @PutMapping("update-admin-prof/{id}")
    public ResponseEntity<String> updateAdminProf(@PathVariable Long id, @RequestBody ManagerProfileDto request) {
        return ResponseEntity.ok(managerService.updateAdminProf(id,request));
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

    @PostMapping("/add-sales")
    public ResponseEntity<String> addSale(@RequestBody SalesDto salesDto){
        String productName = salesDto.getProductName();
        String description = salesDto.getDescription();;
        int quantity = salesDto.getQuantity();
        double price = salesDto.getPrice();

        return ResponseEntity.ok(salesServices.addSales(productName,quantity,price,description));
    }

    @GetMapping("/date/{date}")
    public ResponseEntity<ApiResponse<SalesRecord>> getSalesForSpecificDate(@PathVariable String date) {
        try {
            LocalDate specificDate = LocalDate.parse(date);
            Optional<SalesRecord> sales = salesRepository.findBySaleDate(specificDate);

            if (sales.isPresent()) {
                // Return the sales record with a success message
                ApiResponse<SalesRecord> response = new ApiResponse<>("Sales record found.", sales.get());
                return ResponseEntity.ok(response);
            } else {
                // Return a "No Records Found" message
                ApiResponse<SalesRecord> response = new ApiResponse<>("No records found for the given date.", null);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
        } catch (DateTimeParseException e) {
            // Return an error message for invalid date format
            ApiResponse<SalesRecord> response = new ApiResponse<>("Invalid date format! Use yyyy-MM-dd.", null);
            return ResponseEntity.badRequest().body(response);
        }
    }


    //Retrieve Daily Sales
    @GetMapping("/daily")
    public ResponseEntity<SalesRecord> getDailySales(){
        return ResponseEntity.ok(salesServices.getDailySales());
    }

    @GetMapping("/last-week")
    public ResponseEntity<ApiResponse<List<SalesRecord>>> getLastWeekSales() {
        // Calculate the end date as the last day of the previous week (Sunday)
        LocalDate end = LocalDate.now().with(TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY));
        // Calculate the start date as the first day of the last week (Monday)
        LocalDate start = end.minusWeeks(1).plusDays(1);

        // Log the date range for debugging
        System.out.println("Fetching sales from: " + start + " to " + end);

        List<SalesRecord> sales = salesRepository.findSalesInRange(start, end);

        // Log the fetched sales for debugging
        System.out.println("Fetched Sales: " + sales);

        if (!sales.isEmpty()) {
            ApiResponse<List<SalesRecord>> response = new ApiResponse<>("Last week's sales found.", sales);
            return ResponseEntity.ok(response);
        } else {
            ApiResponse<List<SalesRecord>> response = new ApiResponse<>("No records found for the last week.", null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    //Retrieve Monthly Sales
    @GetMapping("/monthly")
    public ResponseEntity<List<SalesRecord>> getMonthlySales(){
        return ResponseEntity.ok(salesServices.getMonthlySales());
    }

    //Retrieve Weekly Sales
    @GetMapping("/yearly")
    public ResponseEntity<List<SalesRecord>> getYearlySales(){
        return ResponseEntity.ok(salesServices.getYearlySales());
    }


}
