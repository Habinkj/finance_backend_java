package com.example.demo.controller;

import com.example.demo.dto.AIAnalysisDTO;
import com.example.demo.service.AIIntegrationService;
import com.example.demo.dto.CategorySpendDTO;
import com.example.demo.entity.Expense;
import com.example.demo.entity.UserEntity;
import com.example.demo.service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/expenses")
public class ExpenseController {

    @Autowired
    private ExpenseService service;

    @Autowired
    private AIIntegrationService aiService;

    @PostMapping("/add")
    public Expense addExpense(@RequestBody Expense expense, @AuthenticationPrincipal UserEntity currentUser) {
        expense.setUser(currentUser);
        return service.addExpenseSecure(expense);
    }

    // 🔥 Paginated GET: e.g., /expenses?page=0&size=10&sortBy=amount
    @GetMapping
    public Page<Expense> getMyExpenses(
            @AuthenticationPrincipal UserEntity currentUser,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy).descending());
        return service.getExpensesByUser(currentUser.getId(), pageable);
    }

    // 🔥 The AI Data Feeder API
    @GetMapping("/analytics")
    public List<CategorySpendDTO> getMyAnalytics(@AuthenticationPrincipal UserEntity currentUser) {
        return service.getSpendAnalytics(currentUser.getId());
    }
    @GetMapping("/insights")
    public AIAnalysisDTO getAIFinancialInsights(@AuthenticationPrincipal UserEntity currentUser) {
        // 1. Get the aggregated data (Day 7)
        List<CategorySpendDTO> spendData = service.getSpendAnalytics(currentUser.getId());

        // 2. Send it to Python for analysis (Day 8)
        return aiService.requestBehaviorAnalysis(spendData);
    }
    @GetMapping("/whoami")
    public String whoAmI(@AuthenticationPrincipal UserEntity currentUser) {
        if (currentUser == null) {
            return "Spring Security has NO IDEA who you are. The context is empty.";
        }
        return "You are authenticated as: " + currentUser.getEmail() + " with role: " + currentUser.getRole();
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.deleteExpense(id);
    }
}