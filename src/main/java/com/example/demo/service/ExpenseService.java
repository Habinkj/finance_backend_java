package com.example.demo.service;

import com.example.demo.entity.Expense;
import com.example.demo.repository.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExpenseService {

    @Autowired
    private ExpenseRepository repo;

    // 🔥 NEW METHOD (no token here anymore)
    public Expense addExpenseSecure(Expense expense) {

        // basic validation
        if (expense.getAmount() <= 0) {
            throw new RuntimeException("Amount must be positive");
        }

        // 🔴 TEMP: user linking removed (will be added properly via filter next)
        return repo.save(expense);
    }

    public List<Expense> getAllExpenses() {
        return repo.findAll();
    }

    public List<Expense> getExpensesByUser(Long userId) {
        return repo.findByUserId(userId);
    }

    public void deleteExpense(Long id) {
        repo.deleteById(id);
    }
}