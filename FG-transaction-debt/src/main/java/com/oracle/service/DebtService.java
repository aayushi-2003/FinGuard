package com.oracle.service;

import java.util.List;

import com.oracle.model.Debt;
import com.oracle.model.PaymentSchedule;
import com.oracle.model.DebtStatus;

public interface DebtService {
	Debt addDebtService(Debt t);
	Debt searchDebtById(int id);
	Debt updateDebtService(Debt t, int id);
	void deleteDebt(int id);
	List<Debt> allDebtService();
	List<Debt> searchByPaymentSchedule(PaymentSchedule paymentschedule);
	List<Debt> searchByDebtStatus(DebtStatus status);
	

}
