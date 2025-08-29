package com.oracle.controller;


import com.oracle.model.Holding;
import com.oracle.model.Portfolio;
import com.oracle.service.PortfolioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/portfolios")
public class PortfolioController {

    private final PortfolioService portfolioService;

    @Autowired
    public PortfolioController(PortfolioService portfolioService) {
        this.portfolioService = portfolioService;
    }

    // ----- Portfolio APIs -----

    // Create new portfolio
    @PostMapping
    public ResponseEntity<Portfolio> createPortfolio(@RequestBody Portfolio portfolio) {
        return ResponseEntity.ok(portfolioService.createPortfolio(portfolio));
    }

    // Get all portfolios
    @GetMapping
    public ResponseEntity<List<Portfolio>> getAllPortfolios() {
        return ResponseEntity.ok(portfolioService.getAllPortfolios());
    }

    // Get portfolios by userId
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Portfolio>> getPortfoliosByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(portfolioService.getPortfoliosByUser(userId));
    }

    // Get portfolio by id
    @GetMapping("/{id}")
    public ResponseEntity<Portfolio> getPortfolioById(@PathVariable Long id) {
        return ResponseEntity.ok(portfolioService.getPortfolioById(id));
    }

    // Update portfolio
    @PutMapping("/{id}")
    public ResponseEntity<Portfolio> updatePortfolio(@PathVariable Long id, @RequestBody Portfolio portfolio) {
        return ResponseEntity.ok(portfolioService.updatePortfolio(id, portfolio));
    }

    // Delete portfolio
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePortfolio(@PathVariable Long id) {
        portfolioService.deletePortfolio(id);
        return ResponseEntity.noContent().build();
    }

    // ----- Holding APIs -----

    // Add holding to a portfolio
    @PostMapping("/{portfolioId}/holdings")
    public ResponseEntity<Holding> addHolding(@PathVariable Long portfolioId, @RequestBody Holding holding) {
        return ResponseEntity.ok(portfolioService.addHolding(portfolioId, holding));
    }

    // Get holdings by portfolioId
    @GetMapping("/{portfolioId}/holdings")
    public ResponseEntity<List<Holding>> getHoldingsByPortfolio(@PathVariable Long portfolioId) {
        return ResponseEntity.ok(portfolioService.getHoldingsByPortfolio(portfolioId));
    }

    // Update holding
    @PutMapping("/holdings/{holdingId}")
    public ResponseEntity<Holding> updateHolding(@PathVariable Long holdingId, @RequestBody Holding holding) {
        return ResponseEntity.ok(portfolioService.updateHolding(holdingId, holding));
    }

    // Delete holding
    @DeleteMapping("/holdings/{holdingId}")
    public ResponseEntity<Void> deleteHolding(@PathVariable Long holdingId) {
        portfolioService.deleteHolding(holdingId);
        return ResponseEntity.noContent().build();
    }
}

