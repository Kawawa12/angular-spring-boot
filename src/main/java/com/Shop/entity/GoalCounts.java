package com.Shop.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Entity
public class GoalCounts {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int maxProdGoal;

    private int maxCatCount;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public GoalCounts() {
    }

    public GoalCounts(Long id, LocalDateTime updatedAt, LocalDateTime createdAt, int maxCatCount, int maxProdGoal) {
        this.id = id;
        this.updatedAt = updatedAt;
        this.createdAt = createdAt;
        this.maxCatCount = maxCatCount;
        this.maxProdGoal = maxProdGoal;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public int getMaxCatCount() {
        return maxCatCount;
    }

    public void setMaxCatCount(int maxCatCount) {
        this.maxCatCount = maxCatCount;
    }

    public int getMaxProdGoal() {
        return maxProdGoal;
    }

    public void setMaxProdGoal(int maxProdGoal) {
        this.maxProdGoal = maxProdGoal;
    }
}
