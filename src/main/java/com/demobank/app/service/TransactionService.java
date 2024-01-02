package com.demobank.app.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.demobank.app.CommonMessages;
import com.demobank.app.CommonUtil;
import com.demobank.app.model.AccountTransaction;
import com.demobank.app.model.CustomerAccount;
import com.demobank.app.model.TransactionType;

public class TransactionService {

	private static List<AccountTransaction> transactionList = new ArrayList<>();

	public String generateTransactionId(LocalDate transactionDate) {
		int dayTranCount = transactionList.stream().filter(t -> t.getTransactionDate().equals(transactionDate))
				.collect(Collectors.toList()).size();
		return CommonUtil.toDateString(transactionDate) + "-"
				+ CommonUtil.padLeft(String.valueOf(dayTranCount + 1), 3, "0");
	}

	public Boolean processTransaction(LocalDate transactionDate, String accountNo, String transactionType,
			BigDecimal transactionAmount) {
		CustomerAccount custAccount = AccountService.getCustAccountByAccountNo(accountNo);
		if (custAccount != null) {
			BigDecimal accountBalance = custAccount.getAccountBalance();
			// Business Validation
			if (transactionType.equalsIgnoreCase(TransactionType.W_TAG.transactionType)
					&& custAccount.getAccountBalance().subtract(transactionAmount).compareTo(BigDecimal.ZERO) < 0) {
				System.out.println(CommonMessages.ERROR_TAG + CommonMessages.NO_BALANCE_FOR_W_MESSAGE);
				return false;
			}

			// Process Transaction
			String transactionId = generateTransactionId(transactionDate);
			AccountTransaction accountTransaction = new AccountTransaction(transactionId, transactionDate, accountNo,
					transactionType, transactionAmount);
			addTransaction(accountTransaction);
			// Update Account Balance
			if (transactionType.equalsIgnoreCase(TransactionType.W_TAG.transactionType)) {
				accountBalance = accountBalance.subtract(transactionAmount).setScale(2, RoundingMode.HALF_UP);
			} else {
				accountBalance = accountBalance.add(transactionAmount).setScale(2, RoundingMode.HALF_UP);
			}
			AccountService.updateAccountBalance(accountNo, accountBalance);
		}
		return true;
	}

	public static void addTransaction(AccountTransaction accountTransaction) {
		transactionList.add(accountTransaction);
	}

	public static List<AccountTransaction> getDateWiseSortedTransactionsByAccountNo(String accountNo) {
		return transactionList.stream().filter(t -> t.getAccountNo().equalsIgnoreCase(accountNo))
				.sorted((o1, o2) -> o1.getTransactionDate().compareTo(o2.getTransactionDate()))
				.collect(Collectors.toList());
	}

	public static List<AccountTransaction> getDateWiseSortedTransactionsBtwPeriodByAccountNo(String accountNo,
			LocalDate startDate, LocalDate endDate) {
		return transactionList.stream().filter(t -> t.getAccountNo().equalsIgnoreCase(accountNo)).filter(
				t -> t.getTransactionDate().compareTo(startDate) >= 0 && t.getTransactionDate().compareTo(endDate) <= 0)
				.sorted((o1, o2) -> o1.getTransactionDate().compareTo(o2.getTransactionDate()))
				.collect(Collectors.toList());
	}

	public static BigDecimal getAsOnDateAccountBalance(String accountNo, LocalDate asOnDate) {
		BigDecimal accountBalance = BigDecimal.ZERO;
		List<AccountTransaction> accTranListAsOnPrevDate = transactionList.stream()
				.filter(t -> t.getAccountNo().equalsIgnoreCase(accountNo))
				.filter(t -> t.getTransactionDate().compareTo(asOnDate) <= 0).collect(Collectors.toList());
		for (AccountTransaction accountTransaction : accTranListAsOnPrevDate) {
			if (accountTransaction.getTransactionType().equalsIgnoreCase(TransactionType.W_TAG.transactionType)) {
				accountBalance = accountBalance.subtract(accountTransaction.getTransactionAmount()).setScale(2,
						RoundingMode.HALF_UP);
			} else {
				accountBalance = accountBalance.add(accountTransaction.getTransactionAmount()).setScale(2,
						RoundingMode.HALF_UP);
			}
		}
		return accountBalance;
	}

	public void printTransactionStatementByAccountNo(String accountNo) {
		CustomerAccount custAccount = AccountService.getCustAccountByAccountNo(accountNo);
		if (custAccount != null) {
			List<AccountTransaction> accTran = getDateWiseSortedTransactionsByAccountNo(accountNo);
			System.out.println("Account: " + custAccount.getAccountNo() + "       Account Balance: "
					+ custAccount.getAccountBalance());
			System.out.println("| Date     | Txn Id       | Type | Amount           |");
			if (accTran != null) {
				for (AccountTransaction accountTransaction : accTran) {
					System.out.println("| " + CommonUtil.toDateString(accountTransaction.getTransactionDate()) + " | "
							+ CommonUtil.padRight(accountTransaction.getTransactionId(), 12, " ") + " | "
							+ accountTransaction.getTransactionType() + "    | "
							+ CommonUtil.padRight(accountTransaction.getTransactionAmount().toString(), 16, " ")
							+ " |");
				}
				System.out.println();
			}
		}
	}
}