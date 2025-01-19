package com.Shop.serveices;

import com.Shop.dto.ProductDto;
import com.Shop.response.ProductResp;
import com.Shop.response.Response;
import org.springframework.web.multipart.MultipartFile;

public interface ProductService {

    Response addCategory(String name);

    Response addProduct(ProductDto productDto, MultipartFile imageFile);

    Response updateProduct(ProductDto productDto, MultipartFile imageFile);

    ProductResp getProducts();

    ProductResp getProductById(Long prdId, Long catId);

}
