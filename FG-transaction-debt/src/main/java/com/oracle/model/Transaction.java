package com.oracle.model;
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
@Table(name = "Transaction_details")
public class Transaction {
	  @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long transactionId;

	    @Column(nullable = false)
	    private int userId;
	    
		@Enumerated(EnumType.STRING)
		private TransactionType transactionType;
			    	    
		private int amount;
		
		private float postBalance;
	    
	    private String transactionDate;
	    
	    private String description;
}
