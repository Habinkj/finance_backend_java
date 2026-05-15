package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CategorySpendDTO {

    private String category;

    // 🔥 This translates the variable name for Python during the network transfer
    @JsonProperty("amount")
    private Long totalAmount;

    public CategorySpendDTO(String category, Long totalAmount) {
        this.category = category;
        this.totalAmount = totalAmount;
    }

    // Getters and Setters
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Long getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Long totalAmount) {
        this.totalAmount = totalAmount;
    }
}