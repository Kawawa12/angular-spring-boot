package com.Shop.serveices;


import com.Shop.dto.ProductDto;
import com.Shop.response.Response;

import java.util.List;

public interface ProductService {

    ProductDto addProduct(ProductDto productDto);

    List<ProductDto> getAllProducts();

    Response activateProduct(Long id);

    Response deActivateProduct(Long id);

    List<ProductDto> getAllActiveProducts();

    List<ProductDto> getAllDeactivatedProducts();

}
