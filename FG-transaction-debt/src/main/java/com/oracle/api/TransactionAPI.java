package com.oracle.api;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.oracle.model.Transaction;
import com.oracle.model.TransactionType;
import com.oracle.proxy.UserProxy;
import com.oracle.service.TransactionService;


@RestController
@RequestMapping("/transactions")
public class TransactionAPI {
	@Autowired
	private TransactionService transaction_service;
	
	@GetMapping("test")
	public String test() {
		return "Text";
	}
	
	@PostMapping
	public Transaction addNewProduct(@RequestBody Transaction p) {
		return transaction_service.addTransactionService(p);
	}
	
	@GetMapping("/{id}")
	public Transaction searchById(@PathVariable("id") int id) {
		return transaction_service.searchTransactionById(id);
	}
	
	@PutMapping("/{id}")
	public Transaction updateProduct(@RequestBody Transaction p, @PathVariable("id") int id) {
		return transaction_service.updateTransactionService(p, id);
	}
	
	
	@DeleteMapping("/{id}")
	public void deleteProduct(@PathVariable int id) {
		transaction_service.deleteTransaction(id);
	}
	
	@GetMapping("/all")
	public List<Transaction> getAllProducts(){
		return transaction_service.allTransactionService();
	}
	
	@GetMapping("searchbyname")
	public List<Transaction> searchByProductName(@RequestParam("name") TransactionType name){
		return transaction_service.searchByTransactionName(name);
	}
	
	@Autowired
	private UserProxy userproxy;
	@GetMapping("/balance/{uid}")
	 public float feignClient(@PathVariable("uid")  long uid) {
		float balance = userproxy.getBalanceById(uid);
	    System.out.println("Fetched balance from user-service: " + balance);
	    return balance;
	 }

}




