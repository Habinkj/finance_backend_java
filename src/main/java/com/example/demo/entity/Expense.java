package com.example.demo.entity;

import com.example.demo.entity.UserEntity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
public class Expense {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String category;
    private int amount;

    // 🔥 ADD THIS HERE
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    private UserEntity user;

    // getters
    public Long getId() {
        return id;
    }

    public String getCategory() {
        return category;
    }

    public int getAmount() {
        return amount;
    }

    public UserEntity getUser() {
        return user;
    }

    // setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }
}