package com.Shop.serveices;

import com.Shop.entity.SalesRecord;
import com.Shop.repo.SalesRepository;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;


@Service
public class SalesServices {

    private final SalesRepository salesRepository;

    public SalesServices(SalesRepository salesRepository) {
        this.salesRepository = salesRepository;
    }

    // Add Sales Manually
    public String addSales(String productName, int quantity, double price, String description) {
        LocalDate today = LocalDate.now();

        // Check if day's sales record exists
        SalesRecord sales = salesRepository.findBySaleDate(today)
                .orElseGet(() -> {
                    SalesRecord newSales = new SalesRecord();
                    newSales.setSaleDate(today);
                    return salesRepository.save(newSales);
                });

        // Add new product details
        sales.addSales(productName, quantity, price, description);
        salesRepository.save(sales);

        return "Sales added successfully!";
    }

    // Get Daily Sales
    public SalesRecord getDailySales() {
        return salesRepository.findBySaleDate(LocalDate.now()).orElse(null);
    }


    // Get Monthly Sales
    public List<SalesRecord> getMonthlySales() {
        LocalDate start = LocalDate.now().withDayOfMonth(1);
        LocalDate end = LocalDate.now().withDayOfMonth(LocalDate.now().lengthOfMonth());
        return salesRepository.findSalesInRange(start, end);
    }

    // Get Yearly Sales
    public List<SalesRecord> getYearlySales() {
        LocalDate start = LocalDate.now().withDayOfYear(1);
        LocalDate end = LocalDate.now().withDayOfYear(LocalDate.now().getDayOfYear());
        return salesRepository.findSalesInRange(start, end);
    }
}