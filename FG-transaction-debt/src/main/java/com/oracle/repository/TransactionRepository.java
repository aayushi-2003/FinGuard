package com.oracle.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.oracle.model.Transaction;
import com.oracle.model.TransactionType;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
    List<Transaction> findByTransactionType(TransactionType transaction_type);
    List<Transaction> findByUserId(Long userId);
}