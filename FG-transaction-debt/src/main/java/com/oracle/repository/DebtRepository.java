package com.oracle.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.oracle.model.Debt;
import com.oracle.model.PaymentSchedule;
import com.oracle.model.DebtStatus;

import java.util.List;

public interface DebtRepository extends JpaRepository<Debt, Integer> {
    List<Debt> findByPaymentSchedule(PaymentSchedule paymentSchedule);
    List<Debt> findByDebtStatus(DebtStatus debtStatus);
}