package com.oracle.budgetservice.repository;



import org.springframework.data.jpa.repository.JpaRepository;
import com.oracle.budgetservice.model.Budget;
import java.util.List;

public interface BudgetRepository extends JpaRepository<Budget, Long> {
    List<Budget> findByUserId(Long userId);
}