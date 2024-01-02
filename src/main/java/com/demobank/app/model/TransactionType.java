package com.demobank.app.model;

public enum TransactionType {
	D_TAG("D", "Deposit"), W_TAG("W", "Withdrawal"), I_TAG("I", "Interest Posting");

	/* Transaction Type */
	public final String transactionType;

	/* Transaction Type Name */
	public final String transactionTypeName;

	/**
	 * @param transactionType     Transaction Type
	 * @param transactionTypeName Transaction Type Name
	 */
	private TransactionType(String transactionType, String transactionTypeName) {
		this.transactionType = transactionType;
		this.transactionTypeName = transactionTypeName;
	}
}