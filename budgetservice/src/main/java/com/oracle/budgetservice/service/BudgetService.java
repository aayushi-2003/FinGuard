package com.oracle.budgetservice.service;


import com.oracle.budgetservice.model.Budget;
import com.oracle .budgetservice.repository.BudgetRepository;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class BudgetService {
    private final BudgetRepository budgetRepository;
    public BudgetService(BudgetRepository budgetRepository) {
        this.budgetRepository = budgetRepository;
    }
    public Budget createBudget(Budget budget) {
        return budgetRepository.save(budget);
    }
    public List<Budget> getBudgetsByUserId(Long userId) {
        return budgetRepository.findByUserId(userId);
    }
    public Optional<Budget> getBudgetById(Long id) {
        return budgetRepository.findById(id);
    }
    public Budget updateBudget(Long id, Budget updatedBudget) {
        return budgetRepository.findById(id).map(budget -> {
            budget.setAmount(updatedBudget.getAmount());
            budget.setPeriod(updatedBudget.getPeriod());
            return budgetRepository.save(budget);
        }).orElseThrow(() -> new RuntimeException("Budget not found"));
    }
    public void deleteBudget(Long id) {
        budgetRepository.deleteById(id);
    }
}