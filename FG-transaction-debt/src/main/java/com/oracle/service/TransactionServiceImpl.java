package com.oracle.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.oracle.exception.ApplicationException;
import com.oracle.model.Transaction;
import com.oracle.model.TransactionType;
import com.oracle.proxy.UserProxy;
import com.oracle.repository.TransactionRepository;

import jakarta.transaction.Transactional;

@Component
@Transactional
public class TransactionServiceImpl implements TransactionService{
	@Autowired
	private TransactionRepository transaction_repo;
	
	@Autowired
    private UserProxy userProxy;
	
	public Transaction addTransactionService(Transaction t) {
		float currentBalance = userProxy.getBalanceById((long) t.getUserId());

        float postBalance;
        if (t.getTransactionType() == TransactionType.CREDIT) {
            postBalance = currentBalance + t.getAmount();
        } else if (t.getTransactionType() == TransactionType.DEBIT) {
            postBalance = currentBalance - t.getAmount();
        } else {
            throw new IllegalArgumentException("Unknown transaction type");
        }

        t.setPostBalance(postBalance);
        t.setTransactionDate(java.time.LocalDate.now().toString());
        userProxy.updateBalance((long) t.getUserId(), postBalance);

        return transaction_repo.save(t);

	};
	
	public Transaction searchTransactionById(int id) {
		return transaction_repo.findById(id).orElseThrow(()->new ApplicationException("Transaction id " + id +" not found"));

	};

	public Transaction updateTransactionService(Transaction t, int id) {
		transaction_repo.findById(id).orElseThrow(()->new ApplicationException("Transaction id " + id +" not found"));
		return transaction_repo.save(t);
	};
	
	public void deleteTransaction(int id) {
		transaction_repo.deleteById(id);

	};
	public List<Transaction> allTransactionService(){
		return transaction_repo.findAll();
	};
	
	public List<Transaction> searchByTransactionName(TransactionType transactionType){
		return transaction_repo.findByTransactionType(transactionType);
	};
	
}