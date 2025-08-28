package com.oracle.budgetservice.service;

import com.oracle.budgetservice.model.Goal;
import com.oracle.budgetservice.repository.GoalRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class GoalService {
    private final GoalRepository goalRepository;

    public GoalService(GoalRepository goalRepository) {
        this.goalRepository = goalRepository;
    }

    public List<Goal> getGoalsByUserId(Long userId) {
        return goalRepository.findByUserId(userId);
    }

    public Goal saveGoal(Goal goal) {
        return goalRepository.save(goal);
    }

    public void deleteGoal(Long goalId) {
        goalRepository.deleteById(goalId);
    }
}
