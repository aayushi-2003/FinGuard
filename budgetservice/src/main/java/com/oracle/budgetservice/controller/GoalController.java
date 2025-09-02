package com.oracle.budgetservice.controller;

import com.oracle.budgetservice.model.Goal;
import com.oracle.budgetservice.model.Budget;
import com.oracle.budgetservice.repository.GoalRepository;
import com.oracle.budgetservice.repository.BudgetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/goals")
public class GoalController {

    @Autowired
    private GoalRepository goalRepository;

    @Autowired
    private BudgetRepository budgetRepository;

    // 1. Add a Goal and Link it to a Budget
    @PostMapping("/add")
    public Goal addGoal(@RequestBody Goal goal) {
        // Fetch the Budget by ID to link with the Goal
        Optional<Budget> budgetOpt = budgetRepository.findById(goal.getBudget().getId());

        // Check if the Budget exists
        if (budgetOpt.isPresent()) {
            // Link the Goal with the Budget
            goal.setBudget(budgetOpt.get());

            // Save the Goal (Cascade save will happen for associated entities like Budget if necessary)
            return goalRepository.save(goal);
        } else {
            // Throw an error if the Budget is not found
            throw new RuntimeException("Budget not found with ID: " + goal.getBudget().getId());
        }
    }

    // 2. Get Goals by User ID
    @GetMapping("/user/{userId}")
    public List<Goal> getGoalsByUserId(@PathVariable Long userId) {
        return goalRepository.findByUserId(userId);  // Assuming this method is implemented in GoalRepository
    }

    // 3. Get a Goal by ID
    @GetMapping
    public Goal getGoalById(@RequestHeader("id") Long id) {
        return goalRepository.findById(id).orElseThrow(() -> new RuntimeException("Goal not found"));
    }

    // 4. Delete a Goal by ID
    @DeleteMapping("/{id}")
    public void deleteGoal(@PathVariable Long id) {
        goalRepository.deleteById(id);
    }
}
