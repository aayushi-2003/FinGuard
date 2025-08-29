package com.oracle.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.oracle.model.Portfolio;

import java.util.List;

@Repository
public interface PortfolioRepository extends JpaRepository<Portfolio, Long> {

    // find all portfolios for a given user
    List<Portfolio> findByUserId(Long userId);

    // check if portfolio exists by name for a user (useful to prevent duplicates)
    boolean existsByUserIdAndName(Long userId, String name);
}
