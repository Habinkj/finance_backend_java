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
@RequestMapping("/api/expenses")
public class ExpenseController {

    @Autowired
    private ExpenseService service;

    @Autowired
    private AIIntegrationService aiService;

    private final Long TEMP_USER_ID = 1L;

    // ✅ THE HEARTBEAT (Health Check)
    // This method is strategically placed to return INSTANTLY.
    // It doesn't call the database, so Render gets a 200 OK within milliseconds.
    @GetMapping("/health")
    public String healthCheck() {
        return "ALIVE";
    }

    @PostMapping("/add")
    public Expense addExpense(@RequestBody Expense expense) {
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
        List<CategorySpendDTO> spendData = service.getSpendAnalytics(3L);
        return aiService.requestBehaviorAnalysis(spendData);
    }
}