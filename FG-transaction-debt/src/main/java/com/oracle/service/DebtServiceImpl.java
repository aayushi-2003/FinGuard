package com.oracle.service;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.oracle.exception.ApplicationException;
import com.oracle.model.Debt;
import com.oracle.model.PaymentSchedule;
import com.oracle.model.Transaction;
import com.oracle.model.TransactionType;
import com.oracle.model.DebtStatus;
import com.oracle.repository.DebtRepository;

import jakarta.transaction.Transactional;

@Component
@Transactional
public class DebtServiceImpl implements DebtService {
	@Autowired
	private DebtRepository debtrepository;
	@Override
	public Debt addDebtService(Debt t) {
		return debtrepository.save(t);

	};
	
	  
	
	public Debt searchDebtById(int id) {
		return debtrepository.findById(id).orElseThrow(()->new ApplicationException("Transaction id " + id +" not found"));

	};

	public Debt updateDebtService(Debt t, int id) {
		debtrepository.findById(id).orElseThrow(()->new ApplicationException("Transaction id " + id +" not found"));
		return debtrepository.save(t);
	};
	
	public void deleteDebt(int id) {
		debtrepository.deleteById(id);

	};
	public List<Debt> allDebtService(){
		return debtrepository.findAll();
	};
	
	public List<Debt> searchByPaymentSchedule(PaymentSchedule paymentSchedule){
		return debtrepository.findByPaymentSchedule(paymentSchedule);
	};
	public List<Debt> searchByDebtStatus(DebtStatus debtstatus){
		return debtrepository.findByDebtStatus(debtstatus);

	};

}
