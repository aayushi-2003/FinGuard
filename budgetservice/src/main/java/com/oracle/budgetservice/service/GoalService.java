package com.oracle.budgetservice.service;

import com.oracle.budgetservice.model.Budget;
import com.oracle.budgetservice.model.Goal;
import com.oracle.budgetservice.repository.GoalRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class GoalService {
    private final GoalRepository goalRepository;

    public GoalService(GoalRepository goalRepository) {
        this.goalRepository = goalRepository;
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

}
