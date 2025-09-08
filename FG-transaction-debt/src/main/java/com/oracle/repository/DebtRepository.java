package com.oracle.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.oracle.model.Debt;
import com.oracle.model.PaymentSchedule;
import com.oracle.model.DebtStatus;

import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.List;



public interface DebtRepository extends JpaRepository<Debt, Integer> {
    List<Debt> findByPaymentSchedule(PaymentSchedule paymentSchedule);
    List<Debt> findByDebtStatus(DebtStatus debtStatus);
    List<Debt> findByDueDateAndReminded(LocalDate dueDate, boolean reminded);
}