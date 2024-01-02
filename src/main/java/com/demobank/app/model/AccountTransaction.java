package com.demobank.app.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class AccountTransaction {

	/* Transaction Id */
	private String transactionId;

	/* Transaction Date */
	private LocalDate transactionDate;

	/* Transaction Account No */
	private String accountNo;

	/* Transaction Type */
	private String transactionType;

	/* Transaction Amount */
	private BigDecimal transactionAmount;

	public AccountTransaction(String transactionId, LocalDate transactionDate, String accountNo, String transactionType,
			BigDecimal transactionAmount) {
		this.transactionId = transactionId;
		this.transactionDate = transactionDate;
		this.accountNo = accountNo;
		this.transactionType = transactionType;
		this.transactionAmount = transactionAmount;
	}

	/**
	 * Get Transaction Id
	 * 
	 * @return the transactionId
	 */
	public String getTransactionId() {
		return transactionId;
	}

	/**
	 * Get Transaction Date
	 * 
	 * @return the transactionDate
	 */
	public LocalDate getTransactionDate() {
		return transactionDate;
	}

	/**
	 * Get Transaction Account No
	 * 
	 * @return the accountNo
	 */
	public String getAccountNo() {
		return accountNo;
	}

	/**
	 * Get Transaction Type
	 * 
	 * @return the transactionType
	 */
	public String getTransactionType() {
		return transactionType;
	}

	/**
	 * Get Transaction Amount
	 * 
	 * @return the transactionAmount
	 */
	public BigDecimal getTransactionAmount() {
		return transactionAmount;
	}
}