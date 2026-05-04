package com.example.demo.dto;

public class CategorySpendDTO {
    private String category;
    private Long totalAmount; // SUM() in PostgreSQL returns a BigInt, which maps to Long

    public CategorySpendDTO(String category, Long totalAmount) {
        this.category = category;
        this.totalAmount = totalAmount;
    }

    // Getters and Setters
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    public Long getTotalAmount() { return totalAmount; }
    public void setTotalAmount(Long totalAmount) { this.totalAmount = totalAmount; }
}