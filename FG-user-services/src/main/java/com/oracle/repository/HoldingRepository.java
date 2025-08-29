package com.oracle.repository;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.oracle.model.Holding;
import com.oracle.model.Portfolio;

import java.util.List;

@Repository
public interface HoldingRepository extends JpaRepository<Holding, Long> {

    // get all holdings belonging to a portfolio
    List<Holding> findByPortfolio(Portfolio portfolio);

    // get holdings by asset type (STOCK, MUTUAL_FUND, etc.)
    List<Holding> findByAssetType(String assetType);
}
