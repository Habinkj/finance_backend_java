package com.example.demo.repository;

import com.example.demo.dto.CategorySpendDTO;
import com.example.demo.entity.Expense;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    // 🔥 Replaced List with Page to prevent OutOfMemory crashes
    Page<Expense> findByUserId(Long userId, Pageable pageable);

    // 🔥 AI Aggregation: Calculates total spend per category at the database level
    @Query("SELECT new com.example.demo.dto.CategorySpendDTO(e.category, SUM(e.amount)) " +
            "FROM Expense e WHERE e.user.id = :userId GROUP BY e.category")
    List<CategorySpendDTO> getBehavioralSpendAggregation(@Param("userId") Long userId);
}