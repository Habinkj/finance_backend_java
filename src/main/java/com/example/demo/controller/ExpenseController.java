package com.example.demo.controller;

import com.example.demo.dto.AIAnalysisDTO;
import com.example.demo.service.AIIntegrationService;
import com.example.demo.dto.CategorySpendDTO;
import com.example.demo.entity.Expense;
import com.example.demo.service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/expenses") // Added /api/ for cleaner routing
public class ExpenseController {

    @Autowired
    private ExpenseService service;

    @Autowired
    private AIIntegrationService aiService;

    // 🔥 TEMPORARY BYPASS: Using a hardcoded ID (1L) instead of currentUser
    // This ensures your dashboard actually gets data during development
    private final Long TEMP_USER_ID = 1L;

    @PostMapping("/add")
    public Expense addExpense(@RequestBody Expense expense) {
        // We'll fix the user link later; right now we just need it to save
        return service.addExpenseSecure(expense);
    }

    @GetMapping
    public Page<Expense> getMyExpenses(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy).descending());
        return service.getExpensesByUser(TEMP_USER_ID, pageable);
    }

    @GetMapping("/analytics")
    public List<CategorySpendDTO> getMyAnalytics() {
        return service.getSpendAnalytics(TEMP_USER_ID);
    }

    @GetMapping("/insights")
    public AIAnalysisDTO getAIFinancialInsights() {
        List<CategorySpendDTO> spendData = service.getSpendAnalytics(TEMP_USER_ID);
        return aiService.requestBehaviorAnalysis(spendData);
    }
}