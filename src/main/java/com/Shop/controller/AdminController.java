package com.Shop.controller;

import com.Shop.dto.ProductDto;
import com.Shop.response.ProductResp;
import com.Shop.response.Response;
import com.Shop.serveices.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@CrossOrigin("http//:localhost:4200")
@RequestMapping("/auth/api")
public class AdminController {

    private final ProductService productService;


    public AdminController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/add-category")
    public ResponseEntity<Response> addCategory(@RequestParam String name) {
        return ResponseEntity.ok(productService.addCategory(name));
    }

    @PostMapping("/add-product")
    public ResponseEntity<Response> addProduct(
            @RequestParam("name") String name,
            @RequestParam("catId") Long catId,
            @RequestParam("price") double price,
            @RequestParam("image") MultipartFile image
    ){
        //Create the Dto product
        ProductDto product = new ProductDto();
        product.setName(name);
        product.setPrice(price);
        product.setCatId(catId);

        return ResponseEntity.ok(productService.addProduct(product,image));
    }

    @GetMapping("get-product")
    public ResponseEntity<ProductResp> getProduct(@RequestParam Long prodId, @RequestParam Long catId) {
        return ResponseEntity.ok(productService.getProductById(prodId,catId));
    }
}
