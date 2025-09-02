package com.oracle.budgetservice.model;

import jakarta.persistence.*;

@Entity
@Table(name = "goals")  // Make sure to define the table
public class Goal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Double targetAmount;

    @Column(nullable = false)
    private Double savedAmount;

    @Column
    private String deadline;

    @ManyToOne(fetch = FetchType.LAZY)  // Correct mapping for the relationship
    @JoinColumn(name = "budget_id", nullable = false)  // Specify the foreign key column
    private Budget budget;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getTargetAmount() {
        return targetAmount;
    }

    public void setTargetAmount(Double targetAmount) {
        this.targetAmount = targetAmount;
    }

    public Double getSavedAmount() {
        return savedAmount;
    }

    public void setSavedAmount(Double savedAmount) {
        this.savedAmount = savedAmount;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public Budget getBudget() {
        return budget;
    }

    public void setBudget(Budget budget) {
        this.budget = budget;
    }
}
