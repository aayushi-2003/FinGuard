package com.oracle.budgetservice.controller;
import com.oracle.budgetservice.model.Budget;
import com.oracle.budgetservice.service.BudgetService;
import com.oracle.proxy.UserProxy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/budget")
public class BudgetController {
    private final BudgetService budgetService;
    public BudgetController(BudgetService budgetService) {
        this.budgetService = budgetService;
    }

    @PostMapping("/set")
    public ResponseEntity<Budget> createBudget( @RequestBody Budget budget) {
        Budget created = budgetService.createBudget(budget);
        return ResponseEntity.ok(created);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Budget>> getBudgetsByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(budgetService.getBudgetsByUserId(userId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Budget> getBudgetById(@PathVariable Long id) {
        return budgetService.getBudgetById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Budget> updateBudget(@PathVariable Long id, @RequestBody Budget budget) {
        Budget updated = budgetService.updateBudget(id, budget);
        return ResponseEntity.ok(updated);
    }
    
	@Autowired
	private UserProxy userproxy;
	@GetMapping("/balance/{uid}")
	 public float feignClient(@PathVariable("uid")  long uid) {
		float balance = userproxy.getBalanceById(uid);
	    System.out.println("Fetched balance from user-service: " + balance);
	    return balance;
	 }
    
    @PutMapping("/updatespent/{id}")
    public ResponseEntity<Budget> updateSpentAmount(@PathVariable Long id, @RequestBody Budget budget) {
    	Budget updated = budgetService.updateSpentAmount(id, budget);
    	return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteBudget(@PathVariable Long id) {
        budgetService.deleteBudget(id);
        return ResponseEntity.noContent().build();
    }
}