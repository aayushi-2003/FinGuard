package com.oracle.model;
import java.time.LocalDate;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Debt_details")
public class Debt {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int debtId;
	private String debtType;
	private float principleAmt;
	private float interestRate;
	private String startDate;
	private String endDate;
	private String email;
	private boolean reminded;
	
	   private LocalDate dueDate;
	
	@Enumerated(EnumType.STRING)
	private PaymentSchedule paymentSchedule;	
	
	private String nextPaymentDate;
	private float outstandingBalance;
	
	@Enumerated(EnumType.STRING)
	private DebtStatus debtStatus;
}
