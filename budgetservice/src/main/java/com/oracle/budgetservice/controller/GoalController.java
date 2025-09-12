package com.oracle.budgetservice.controller;

import com.oracle.budgetservice.model.Goal;
import com.oracle.budgetservice.service.GoalService;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:8000")
@RestController
@RequestMapping("/goals")
public class GoalController {

    @Autowired
    private GoalService goalservice;
    
    @PostMapping("/add")
    public ResponseEntity<Goal> addGoal( @RequestBody Goal goal) {
        Goal created = goalservice.createGoal(goal);
        return ResponseEntity.ok(created);
    }

    // 2. Get Goals by User ID
    @GetMapping("/user/{userId}")
    public List<Goal> getGoalsByUserId(@PathVariable Long userId) {
        return goalservice.findByUserId(userId);  // Assuming this method is implemented in GoalRepository
    }
    
    @GetMapping("/all")
    public ResponseEntity<List> getAllBudgets() {
    return ResponseEntity.ok(goalservice.getAllBudgets());
    }
    
    @GetMapping("/goals-vs-budget")
    public ResponseEntity<Map<String, Long>> getGoalsVsBudget() {
        Map<String, Long> goalMap = goalservice.getGoalAmountPerBudgetName();
        return ResponseEntity.ok(goalMap);
    }

    // 3. Get a Goal by ID
    @GetMapping
    public Goal getGoalById(@RequestHeader("id") Long id) {
        return goalservice.getGoalById(id).get();
    }

    // 4. Delete a Goal by ID
    @DeleteMapping("/{id}")
    public void deleteGoal(@PathVariable Long id) {
    	goalservice.deleteGoal(id);
    }
}
