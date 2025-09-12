package com.oracle.budgetservice.service;


import com.oracle.budgetservice.model.Budget;
import com.oracle .budgetservice.repository.BudgetRepository;
import com.oracle.proxy.UserProxy;

import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class BudgetService {
    private final BudgetRepository budgetRepository;
    private final UserProxy userProxy;

    public BudgetService(BudgetRepository budgetRepository, UserProxy userProxy) {
        this.budgetRepository = budgetRepository;
        this.userProxy = userProxy;
    }
    
    public List getAllBudgets() {
    	return budgetRepository.findAll();
    	}
    
    public Budget createBudget(Budget budget) {
        Long userId = budget.getUserId();

        float currentBalance = userProxy.getBalanceById(userId);
        List<Budget> existingBudgets = budgetRepository.findByUserId(userId);

        float totalAllocated = 0;
        for (Budget b : existingBudgets) {
            totalAllocated += b.getAllocatedAmount();
        }

        totalAllocated += budget.getAllocatedAmount();

        if (totalAllocated > currentBalance) {
            throw new RuntimeException("Total allocated budget exceeds user's current balance.");
        }

        if (budget.getSpentAmount() == 0.0f) {
            budget.setSpentAmount(0.0f);
        }

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
            budget.setAllocatedAmount(updatedBudget.getAllocatedAmount());
            budget.setPeriodType(updatedBudget.getPeriodType());
            return budgetRepository.save(budget);
        }).orElseThrow(() -> new RuntimeException("Budget not found"));
    }
    
    public Budget updateSpentAmount(Long id, Budget updatedBudget) {
        return budgetRepository.findById(id).map(budget -> {
            float newSpentAmount = updatedBudget.getSpentAmount()+budget.getSpentAmount();
            if (newSpentAmount > budget.getAllocatedAmount()) {
            	System.out.println("Error message");
                throw new RuntimeException("Spent amount cannot be greater than allocated amount.");
            }
            budget.setSpentAmount(newSpentAmount);
            return budgetRepository.save(budget);
        }).orElseThrow(() -> new RuntimeException("Budget not found"));
    }
    
    public void deleteBudget(Long id) {
        budgetRepository.deleteById(id);
    }
}