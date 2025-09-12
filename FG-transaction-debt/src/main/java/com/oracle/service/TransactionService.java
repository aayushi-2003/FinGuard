package com.oracle.service;

import java.util.List;

import com.oracle.model.Transaction;
import com.oracle.model.TransactionType;

public interface TransactionService {
	Transaction addTransactionService(Transaction t);
	Transaction searchTransactionById(int id);
	Transaction updateTransactionService(Transaction t, int id);
	void deleteTransaction(int id);
	List<Transaction> allTransactionService();
	List<Transaction> searchByTransactionName(TransactionType transactionType);
	List<Transaction> findByUserId(Long userId);

}
