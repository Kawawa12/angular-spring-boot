package com.Shop.serveices;

import com.Shop.entity.Product;
import com.Shop.entity.Stock;
import com.Shop.repo.ProductRepo;
import com.Shop.repo.StockRepository;
import com.Shop.response.StockResponseDto;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class StockServiceImpl implements StockService{

    private final StockRepository stockRepository;
    private final ProductRepo productRepo;


    public StockServiceImpl(StockRepository stockRepository, ProductRepo productRepo) {
        this.stockRepository = stockRepository;
        this.productRepo = productRepo;
    }


    @Override
    public List<StockResponseDto> viewProductsStock() {
        return stockRepository.findAll().stream()
                .map(product -> {
                    StockResponseDto responseDto = new StockResponseDto();
                    responseDto.setId(product.getId());
                    responseDto.setProductName(product.getProduct().getName());
                    responseDto.setStockQuantity(product.getStockQuantity());
                    responseDto.setCreatedAt(product.getCreatedAt());
                    responseDto.setUpdatedAt(product.getUpdatedAt());
                    return responseDto;
                }).toList();
    }

    @Override
    public String addStockToProduct(int stockValue, Long id) {
        // Find product by id if it exists
        Optional<Product> product = productRepo.findById(id);
        if (product.isPresent()) {
            Product existProduct = product.get();

            // Find the existing stock for the product
            Stock stock = stockRepository.findByProduct(existProduct);

            // If stock exists, add the new stock value to the existing one
            if (stock != null) {
                // Get the existing stock quantity and add the new value to it
                int currentStock = stock.getStockQuantity();
                stock.setStockQuantity(currentStock + stockValue);
                stock.setProduct(existProduct);
                stock.setUpdatedAt(LocalDateTime.now());
                // Save the updated stock
                stockRepository.save(stock);
            } else {
                // If no stock exists, create a new stock entry for the product
                Stock newStock = new Stock();
                newStock.setProduct(existProduct);
                newStock.setStockQuantity(stockValue);
                newStock.setName(existProduct.getName()); // Or set other relevant fields
                stockRepository.save(newStock);
            }
        }else {
            return "Product is out of stock";
        }
        return "Stock added successfully.";
    }

}
