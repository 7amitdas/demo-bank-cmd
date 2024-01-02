package com.demobank.app.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.demobank.app.CommonUtil;
import com.demobank.app.model.AccountTransaction;
import com.demobank.app.model.CustomerAccount;
import com.demobank.app.model.TransactionType;

public class AccountService {

	private static Map<String, CustomerAccount> accountList = new HashMap<>();

	/**
	 * public Method to get account list
	 * 
	 * @return the accountList
	 */
	public static Map<String, CustomerAccount> getAccountList() {
		return accountList;
	}

	/**
	 * public Method to create Account
	 * 
	 * @param accountNo Account No
	 */
	public void addAccount(String accountNo) {
		if (!accountList.containsKey(accountNo)) {
			accountList.put(accountNo, new CustomerAccount(accountNo, "", BigDecimal.ZERO));
		}
	}

	/**
	 * public Method to get Customer Details by Account No.
	 * 
	 * @param accountNo Account No
	 * @return
	 */
	public static CustomerAccount getCustAccountByAccountNo(String accountNo) {
		if (accountList.containsKey(accountNo)) {
			return accountList.get(accountNo);
		}
		return null;
	}

	/**
	 * public Method to update Account Balance
	 * 
	 * @param accountNo Account No
	 * @param accountBalance Balance to be updated
	 */
	public static void updateAccountBalance(String accountNo, BigDecimal accountBalance) {
		CustomerAccount custAccount = getCustAccountByAccountNo(accountNo);
		custAccount.setAccountBalance(accountBalance);
		accountList.put(accountNo, custAccount);
	}

	/**
	 * public Method to print Account Statement for a Month with Interest Calculation
	 * 
	 * @param accountNo Account No
	 * @param statementPrintDate Statement Print Date
	 */
	public void printAccountStatementByAccountNoAndDate(String accountNo, LocalDate statementPrintDate) {
		CustomerAccount custAccount = AccountService.getCustAccountByAccountNo(accountNo);
		if (custAccount != null) {
			LocalDate startOfMonth = statementPrintDate.with(TemporalAdjusters.firstDayOfMonth());
			LocalDate endOfMonth = statementPrintDate.with(TemporalAdjusters.lastDayOfMonth());
			LocalDate prevDateBeforeStartofMonth = startOfMonth.minusDays(1);
			BigDecimal accountBalance = TransactionService.getAsOnDateAccountBalance(accountNo,
					prevDateBeforeStartofMonth);
			List<AccountTransaction> accTransactionList = TransactionService
					.getDateWiseSortedTransactionsBtwPeriodByAccountNo(accountNo, startOfMonth, endOfMonth);
			System.out.println("Account: " + custAccount.getAccountNo() + "      		 As On "
					+ CommonUtil.toDateString(prevDateBeforeStartofMonth) + " Account Balance: " + accountBalance);
			System.out.println("| Date     | Txn Id       | Type | Amount           | Balance          |");
			if (accTransactionList != null) {
				for (AccountTransaction accountTransaction : accTransactionList) {
					if (accountTransaction.getTransactionType()
							.equalsIgnoreCase(TransactionType.W_TAG.transactionType)) {
						accountBalance = accountBalance.subtract(accountTransaction.getTransactionAmount()).setScale(2,
								RoundingMode.HALF_UP);
					} else {
						accountBalance = accountBalance.add(accountTransaction.getTransactionAmount()).setScale(2,
								RoundingMode.HALF_UP);
					}
					System.out.println("| " + CommonUtil.toDateString(accountTransaction.getTransactionDate()) + " | "
							+ CommonUtil.padRight(accountTransaction.getTransactionId(), 12, " ") + " | "
							+ accountTransaction.getTransactionType() + "    | "
							+ CommonUtil.padRight(accountTransaction.getTransactionAmount().toString(), 16, " ") + " | "
							+ CommonUtil.padRight(accountBalance.toString(), 16, " ") + " |");
				}

				// Interest
				InterestRateService.printCalculatedInterestAmountForStatement(accountNo, accTransactionList,
						startOfMonth, endOfMonth, accountBalance);
			}
		}
	}
}