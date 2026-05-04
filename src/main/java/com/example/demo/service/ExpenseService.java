package com.example.demo.service;

import com.example.demo.dto.CategorySpendDTO;
import com.example.demo.entity.Expense;
import com.example.demo.repository.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExpenseService {

    @Autowired
    private ExpenseRepository repo;

    public Expense addExpenseSecure(Expense expense) {
        if (expense.getAmount() <= 0) {
            throw new RuntimeException("Amount must be positive");
        }
        return repo.save(expense);
    }

    // 🔥 Pagination active
    public Page<Expense> getExpensesByUser(Long userId, Pageable pageable) {
        return repo.findByUserId(userId, pageable);
    }

    // 🔥 Aggregation active
    public List<CategorySpendDTO> getSpendAnalytics(Long userId) {
        return repo.getBehavioralSpendAggregation(userId);
    }

    public void deleteExpense(Long id) {
        repo.deleteById(id);
    }
}