package com.demobank.app.model;

import java.math.BigDecimal;

public class CustomerAccount {

	/* Customer Account No */
	private String accountNo;

	/* Customer Account Name */
	private String accountName;

	/* Account Balance */
	private BigDecimal accountBalance;

	public CustomerAccount(String accountNo, String accountName, BigDecimal accountBalance) {
		this.accountNo = accountNo;
		this.accountName = accountName;
		this.accountBalance = accountBalance;
	}

	/**
	 * Get Customer Account Name
	 * 
	 * @return the accountNo
	 */
	public String getAccountNo() {
		return accountNo;
	}

	/**
	 * Get Customer Account Name
	 * 
	 * @return the accountName
	 */
	public String getAccountName() {
		return accountName;
	}

	/**
	 * Set Customer Account Name
	 * 
	 * @param accountName the accountName to set
	 */
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	/**
	 * Get Account Balance
	 * 
	 * @return the accountBalance
	 */
	public BigDecimal getAccountBalance() {
		return accountBalance;
	}

	/**
	 * Set Account Balance
	 * 
	 * @param accountBalance the accountBalance to set
	 */
	public void setAccountBalance(BigDecimal accountBalance) {
		this.accountBalance = accountBalance;
	}
}