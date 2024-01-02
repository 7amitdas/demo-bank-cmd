package com.demobank.app;

import java.math.BigDecimal;

import com.demobank.app.model.CustomerAccount;
import com.demobank.app.model.TransactionType;
import com.demobank.app.service.AccountService;
import com.demobank.app.service.BankingServicesList;

public class UserInputValidation {

	public static Boolean isValidUserAction(String userAction) {
		if (CommonUtil.isBlank(userAction) || !BankingServicesList.findByServiceCode(userAction)) {
			System.out.println(CommonMessages.ERROR_TAG + CommonMessages.INVALID_USER_ACTION);
			BankingServicesList.printServices();
			return false;
		}
		return true;
	}

	public static Boolean validateTransactionDetails(String inputTransactionDate, String inputAccountNo,
			String inputTransactionType, String inputTransactionAmount) {
		if (CommonUtil.isBlank(inputTransactionDate) || CommonUtil.isBlank(inputAccountNo)
				|| CommonUtil.isBlank(inputTransactionType) || CommonUtil.isBlank(inputTransactionAmount)) {
			System.out.println(CommonMessages.ERROR_TAG + CommonMessages.INVALID_TRAN_DETAILS);
			return false;
		} else {
			if (CommonUtil.getLocalDateFromString(inputTransactionDate) == null) {
				System.out.println(CommonMessages.ERROR_TAG + CommonMessages.INVALID_DATE_FORMAT);
				return false;
			}
			if (!(inputTransactionType.equalsIgnoreCase(TransactionType.D_TAG.transactionType)
					|| inputTransactionType.equalsIgnoreCase(TransactionType.W_TAG.transactionType))) {
				System.out.println(CommonMessages.ERROR_TAG + CommonMessages.INVALID_TRAN_TYPE);
				return false;
			}
			try {
				int decPointIndex = inputTransactionAmount.indexOf(".");
				if (decPointIndex != -1) {
					String decimalString = inputTransactionAmount.substring(decPointIndex + 1);
					if (decimalString.length() == 0 || decimalString.length() > 2
							|| Integer.parseInt(decimalString) < 0) {
						System.out.println(CommonMessages.ERROR_TAG + CommonMessages.INVALID_AMOUNT);
						return false;
					}
				}
				BigDecimal tranAmount = new BigDecimal(inputTransactionAmount);
				if (tranAmount.compareTo(BigDecimal.ZERO) == 0) {
					System.out.println(CommonMessages.ERROR_TAG + CommonMessages.INVALID_AMOUNT);
					return false;
				}
			} catch (NumberFormatException e) {
				System.out.println(CommonMessages.ERROR_TAG + CommonMessages.INVALID_AMOUNT);
				return false;
			}
		}
		return true;
	}

	public static Boolean validateInterestRuleDetails(String inIntRateEffectiveDate, String inIntRateRuleId,
			String inInterestRate) {
		if (CommonUtil.isBlank(inIntRateEffectiveDate) || CommonUtil.isBlank(inIntRateRuleId)
				|| CommonUtil.isBlank(inInterestRate)) {
			System.out.println(CommonMessages.ERROR_TAG + CommonMessages.INVALID_INTEREST_RULE_DETAILS);
			return false;
		} else {
			if (CommonUtil.getLocalDateFromString(inIntRateEffectiveDate) == null) {
				System.out.println(CommonMessages.ERROR_TAG + CommonMessages.INVALID_DATE_FORMAT);
				return false;
			}
			try {
				int decPointIndex = inInterestRate.indexOf(".");
				if (decPointIndex != -1) {
					String decimalString = inInterestRate.substring(decPointIndex + 1);
					if (decimalString.length() == 0 || decimalString.length() > 2
							|| Integer.parseInt(decimalString) < 0) {
						System.out.println(CommonMessages.ERROR_TAG + CommonMessages.INVALID_INTEREST_RATE);
						return false;
					}
				}
				BigDecimal tranAmount = new BigDecimal(inInterestRate);
				if (!(tranAmount.compareTo(BigDecimal.ZERO) > 0
						&& tranAmount.compareTo(new BigDecimal("100.00")) < 0)) {
					System.out.println(CommonMessages.ERROR_TAG + CommonMessages.INVALID_INTEREST_RATE);
					return false;
				}
			} catch (NumberFormatException e) {
				System.out.println(CommonMessages.ERROR_TAG + CommonMessages.INVALID_INTEREST_RATE);
				return false;
			}
		}
		return true;
	}

	public static Boolean validatePrintStatementInput(String inAccountNo, String inStatementPrintDate) {
		if (CommonUtil.isBlank(inAccountNo) || CommonUtil.isBlank(inStatementPrintDate)) {
			System.out.println(CommonMessages.ERROR_TAG + CommonMessages.INVALID_PRINT_DETAILS);
			return false;
		} else {
			CustomerAccount custAccount = AccountService.getCustAccountByAccountNo(inAccountNo);
			if (custAccount == null) {
				System.out.println(CommonMessages.ERROR_TAG + CommonMessages.INVALID_ACCOUNT_NO);
				return false;
			}
			if (CommonUtil.getLocalDateFromStringYYYYMM(inStatementPrintDate) == null) {
				System.out.println(CommonMessages.ERROR_TAG + CommonMessages.INVALID_DATE_YYYYMM_FORMAT);
				return false;
			}
		}
		return true;
	}
}