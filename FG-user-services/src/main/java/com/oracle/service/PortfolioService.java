package com.oracle.service;


import com.oracle.repository.PortfolioRepository;
import com.oracle.repository.HoldingRepository;
import com.oracle.exception.PortfolioNotFoundException;
import com.oracle.model.Holding;
import com.oracle.model.Portfolio;
import com.oracle.exception.HoldingNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PortfolioService {

    private final PortfolioRepository portfolioRepository;
    private final HoldingRepository holdingRepository;

    @Autowired
    public PortfolioService(PortfolioRepository portfolioRepository, HoldingRepository holdingRepository) {
        this.portfolioRepository = portfolioRepository;
        this.holdingRepository = holdingRepository;
    }

    // ----- Portfolio methods -----

    // Create new portfolio
    public Portfolio createPortfolio(Portfolio portfolio) {
        return portfolioRepository.save(portfolio);
    }

    // Get all portfolios
    public List<Portfolio> getAllPortfolios() {
        return portfolioRepository.findAll();
    }

    // Get portfolios by userId
    public List<Portfolio> getPortfoliosByUser(Long userId) {
        return portfolioRepository.findByUserId(userId);
    }

    // Get portfolio by ID
    public Portfolio getPortfolioById(Long id) {
        return portfolioRepository.findById(id)
                .orElseThrow(() -> new PortfolioNotFoundException(id));
    }

    // Update portfolio
    public Portfolio updatePortfolio(Long id, Portfolio portfolio) {
        return portfolioRepository.findById(id).map(existing -> {
            existing.setName(portfolio.getName());
            existing.setDescription(portfolio.getDescription());
            return portfolioRepository.save(existing);
        }).orElseThrow(() -> new PortfolioNotFoundException(id));
    }

    // Delete portfolio
    public void deletePortfolio(Long id) {
        if (!portfolioRepository.existsById(id)) {
            throw new PortfolioNotFoundException(id);
        }
        portfolioRepository.deleteById(id);
    }

    // ----- Holding methods -----

    // Add holding to a portfolio
    public Holding addHolding(Long portfolioId, Holding holding) {
        Portfolio portfolio = getPortfolioById(portfolioId); // throws if not found
        holding.setPortfolio(portfolio);
        return holdingRepository.save(holding);
    }

    // Get all holdings by portfolioId
    public List<Holding> getHoldingsByPortfolio(Long portfolioId) {
        Portfolio portfolio = getPortfolioById(portfolioId); // throws if not found
        return holdingRepository.findByPortfolio(portfolio);
    }

    // Update holding
    public Holding updateHolding(Long holdingId, Holding holding) {
        return holdingRepository.findById(holdingId).map(existing -> {
            existing.setAssetType(holding.getAssetType());
            existing.setAssetName(holding.getAssetName());
            existing.setQuantity(holding.getQuantity());
            existing.setPurchasePrice(holding.getPurchasePrice());
            existing.setCurrentPrice(holding.getCurrentPrice());
            return holdingRepository.save(existing);
        }).orElseThrow(() -> new HoldingNotFoundException(holdingId));
    }

    // Delete holding
    public void deleteHolding(Long holdingId) {
        if (!holdingRepository.existsById(holdingId)) {
            throw new HoldingNotFoundException(holdingId);
        }
        holdingRepository.deleteById(holdingId);
    }
}
