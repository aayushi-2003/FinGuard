package com.oracle.budgetservice.service;

import com.oracle.budgetservice.model.Budget;
import com.oracle.budgetservice.model.Goal;
import com.oracle.budgetservice.repository.GoalRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class GoalService {
    private final GoalRepository goalRepository;

    public Map<String, Long> getGoalAmountPerBudgetName() {
        // Returns a map: budgetName -> sum of goal target amounts
        List<Goal> goals = goalRepository.findAll();
        Map<String, Long> goalMap = new HashMap<>();

        for (Goal g : goals) {
            String budgetName = g.getBudgetname(); // Use budgetName instead of budgetId
            goalMap.put(budgetName, (long) (goalMap.getOrDefault(budgetName, 0L) + g.getTargetAmount()));
        }
        return goalMap;
    }


    public GoalService(GoalRepository goalRepository) {
        this.goalRepository = goalRepository;
    }
    
    public List getAllBudgets() {
    	return goalRepository.findAll();
    	}
    
    public Goal createGoal(Goal goal) {

        return goalRepository.save(goal);    
    }

    public Optional<Goal> getGoalById(Long id) {
        return goalRepository.findById(id);
    }

    public Goal saveGoal(Goal goal) {
        return goalRepository.save(goal);
    }

    public void deleteGoal(Long goalId) {
        goalRepository.deleteById(goalId);
    }

    public List<Goal> findByUserId(Long userId) {
        return goalRepository.findByUserId(userId);
    }

}
