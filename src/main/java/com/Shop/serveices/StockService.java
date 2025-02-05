package com.Shop.serveices;

import com.Shop.response.StockResponseDto;

import java.util.List;

public interface StockService {

    List<StockResponseDto> viewProductsStock();

    String addStockToProduct(int stockValue, Long id);
}
