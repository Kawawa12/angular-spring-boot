package com.Shop.repo;

import com.Shop.entity.SalesRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


public interface SalesRepository extends JpaRepository<SalesRecord, Long> {

    @Query("SELECT s FROM SalesRecord s WHERE s.saleDate BETWEEN :start AND :end")
    List<SalesRecord> findSalesInRange(@Param("start") LocalDate start, @Param("end") LocalDate end);

    @Query("SELECT s FROM SalesRecord s WHERE DATE(s.saleDate) = :date")
    Optional<SalesRecord> findBySaleDate(@Param("date") LocalDate date);
}
