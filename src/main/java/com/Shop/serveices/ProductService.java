package com.Shop.serveices;


import com.Shop.dto.ProductDto;

import java.util.List;

public interface ProductService {

    ProductDto addProduct(ProductDto productDto);

    List<ProductDto> getAllProducts();


}
