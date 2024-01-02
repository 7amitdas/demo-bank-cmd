package com.demobank.app;

import java.math.BigDecimal;
import java.util.Scanner;

import com.demobank.app.service.AccountService;
import com.demobank.app.service.BankingServicesList;
import com.demobank.app.service.InterestRateService;
import com.demobank.app.service.TransactionService;

public class StartBankApp {

	public static void main(String[] args) {
		// Enable the following Line for Dummy Data
		// new InitDemoData();
		System.out.println(CommonMessages.WELCOME_MESSAGE);
		BankingServicesList.printServices();
		StartBankApp userDataObj = new StartBankApp();
		Scanner userInput = new Scanner(System.in);
		userDataObj.getUserActionInput(userInput);
		userInput.close();
	}

	/**
	 * Method to take User Input to Access Available Banking Services
	 * 
	 * @return String Selected Service Code
	 */
	public void getUserActionInput(Scanner userInput) {
		String userAction = "";
		if (userInput.hasNext()) {
			userAction = userInput.next();
			if (UserInputValidation.isValidUserAction(userAction)) {
				accessBankingServiceByServiceCode(userAction, userInput, Boolean.FALSE);
			} else {
				getUserActionInput(userInput);
			}
		}
	}

	/**
	 * Method to access Related Banking Services By User Selected Option
	 * 
	 * @return String Selected Service Code
	 */
	public void accessBankingServiceByServiceCode(String userAction, Scanner userInput, Boolean is2ndOccurance) {
		if (userAction.equalsIgnoreCase(BankingServicesList.T_TAG.serviceCode)) {
			String inTransactionDate = "";
			String inAccountNo = "";
			String inTransactionType = "";
			String inTransactionAmount = "";
			AccountService accService = new AccountService();
			TransactionService tranService = new TransactionService();
			if (is2ndOccurance) {
				is2ndOccurance = false;
			} else {
				System.out.println(CommonMessages.TRANSACTION_INPUT_MESSAGE);
			}
			if (userInput.hasNext()) {
				try {
					inTransactionDate = userInput.next();
					if ("B".equalsIgnoreCase(inTransactionDate)) {
						backToMainMenu(userInput);
					}
					inAccountNo = userInput.next();
					inTransactionType = userInput.next();
					inTransactionAmount = userInput.next();
				} catch (Exception e) {
					System.out.println(CommonMessages.SYSTEM_ERROR_MSG);
				}
			}
			inAccountNo = inAccountNo.toUpperCase();
			inTransactionType = inTransactionType.toUpperCase();
			if (UserInputValidation.validateTransactionDetails(inTransactionDate, inAccountNo, inTransactionType,
					inTransactionAmount)) {
				// Check Account Available, Otherwise Create account
				accService.addAccount(inAccountNo);
				// Process Transaction
				if (tranService.processTransaction(CommonUtil.getLocalDateFromString(inTransactionDate), inAccountNo,
						inTransactionType, new BigDecimal(inTransactionAmount))) {
					// Print Statement
					tranService.printTransactionStatementByAccountNo(inAccountNo);
					System.out.println(CommonMessages.ADD_TRAN_MESSAGE);
					BankingServicesList.printServices();
					getUserActionInput(userInput);
				} else {
					System.out.println(CommonMessages.TRANSACTION_INPUT_MESSAGE);
					accessBankingServiceByServiceCode(userAction, userInput, Boolean.TRUE);
				}
			} else {
				System.out.println(CommonMessages.TRANSACTION_INPUT_MESSAGE);
				accessBankingServiceByServiceCode(userAction, userInput, Boolean.TRUE);
			}
		} else if (userAction.equalsIgnoreCase(BankingServicesList.I_TAG.serviceCode)) {
			String inIntRateEffectiveDate = "";
			String inIntRateRuleId = "";
			String inInterestRate = "";
			InterestRateService intRateService = new InterestRateService();
			if (is2ndOccurance) {
				is2ndOccurance = false;
			} else {
				System.out.println(CommonMessages.INTEREST_INPUT_MESSAGE);
			}
			if (userInput.hasNext()) {
				try {
					inIntRateEffectiveDate = userInput.next();
					if ("B".equalsIgnoreCase(inIntRateEffectiveDate)) {
						backToMainMenu(userInput);
					}
					inIntRateRuleId = userInput.next();
					inInterestRate = userInput.next();
				} catch (Exception e) {
					System.out.println(CommonMessages.SYSTEM_ERROR_MSG);
				}
			}
			inIntRateRuleId = inIntRateRuleId.toUpperCase();
			if (UserInputValidation.validateInterestRuleDetails(inIntRateEffectiveDate, inIntRateRuleId,
					inInterestRate)) {
				// Add/Update Interest Rule
				if (intRateService.addOrUpdateInterestRule(CommonUtil.getLocalDateFromString(inIntRateEffectiveDate),
						inIntRateRuleId, new BigDecimal(inInterestRate))) {
					// Print Statement
					intRateService.printDatewiseInterestRules();
					System.out.println(CommonMessages.ADD_TRAN_MESSAGE);
					BankingServicesList.printServices();
					getUserActionInput(userInput);
				} else {
					System.out.println(CommonMessages.INTEREST_INPUT_MESSAGE);
					accessBankingServiceByServiceCode(userAction, userInput, Boolean.TRUE);
				}
			} else {
				System.out.println(CommonMessages.INTEREST_INPUT_MESSAGE);
				accessBankingServiceByServiceCode(userAction, userInput, Boolean.TRUE);
			}
		} else if (userAction.equalsIgnoreCase(BankingServicesList.P_TAG.serviceCode)) {
			String inAccountNo = "";
			String inStatementPrintDate = "";
			AccountService accService = new AccountService();
			if (is2ndOccurance) {
				is2ndOccurance = false;
			} else {
				System.out.println(CommonMessages.STATEMENT_PRINT_INPUT_MESSAGE);
			}
			if (userInput.hasNext()) {
				try {
					inAccountNo = userInput.next();
					if ("B".equalsIgnoreCase(inAccountNo)) {
						backToMainMenu(userInput);
					}
					inStatementPrintDate = userInput.next();
				} catch (Exception e) {
					System.out.println(CommonMessages.SYSTEM_ERROR_MSG);
				}
			}
			inAccountNo = inAccountNo.toUpperCase();
			if (UserInputValidation.validatePrintStatementInput(inAccountNo, inStatementPrintDate)) {
				// Print Statement
				accService.printAccountStatementByAccountNoAndDate(inAccountNo,
						CommonUtil.getLocalDateFromStringYYYYMM(inStatementPrintDate));
				System.out.println(CommonMessages.ADD_TRAN_MESSAGE);
				BankingServicesList.printServices();
				getUserActionInput(userInput);
			} else {
				System.out.println(CommonMessages.STATEMENT_PRINT_INPUT_MESSAGE);
				accessBankingServiceByServiceCode(userAction, userInput, Boolean.TRUE);
			}
		} else {
			System.out.println(CommonMessages.FAIRWELL_MESSAGE);
			System.exit(0);
		}
	}

	public void backToMainMenu(Scanner userInput) {
		System.out.println(CommonMessages.WELCOME_MESSAGE);
		BankingServicesList.printServices();
		getUserActionInput(userInput);
	}
}