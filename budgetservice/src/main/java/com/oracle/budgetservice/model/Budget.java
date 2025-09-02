package com.oracle.budgetservice.model;

import java.util.List;

import jakarta.persistence.*;

@Entity
@Table(name = "budgets")  // Ensure correct table name
public class Budget {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private Double amount;

    @Column(nullable = false)
    private String period;

    @OneToMany(mappedBy = "budget", cascade = CascadeType.ALL, orphanRemoval = true)  // One-to-Many relationship with Goal
    private List<Goal> goals;

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
    public Double getAmount() { return amount; }
    public void setAmount(Double amount) { this.amount = amount; }
    public String getPeriod() { return period; }
    public void setPeriod(String period) { this.period = period; }

    public List<Goal> getGoals() {
        return goals;
    }

    public void setGoals(List<Goal> goals) {
        this.goals = goals;
    }
}
