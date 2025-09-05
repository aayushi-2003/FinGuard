package com.oracle.budgetservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "budgets")
public class Budget {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId;
    
    @Column(nullable = false)
    private String budgetName;

    @Column(nullable = false)
    private float allocatedAmount;

    @Column(nullable = false)
    private String periodType;
    
    @Column
    private float spentAmount;
    
    @Column(nullable = false)
    private String status;
    
    @Column
    private boolean isaGoal;
}
