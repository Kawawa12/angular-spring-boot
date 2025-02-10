package com.Shop.repo;

import com.Shop.entity.SalesRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


public interface SalesRepository extends JpaRepository<SalesRecord, Long> {

    @Query("SELECT s FROM SalesRecord s WHERE s.saleDate BETWEEN :startDate AND :endDate")
    List<SalesRecord> findSalesInRange(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    @Query("SELECT s FROM SalesRecord s WHERE DATE(s.saleDate) = :date")
    Optional<SalesRecord> findBySaleDate(@Param("date") LocalDate date);
}
