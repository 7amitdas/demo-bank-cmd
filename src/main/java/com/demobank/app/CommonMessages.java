package com.demobank.app;

public class CommonMessages {
	// System Message
	public static final String SYSTEM_ERROR_MSG = "System Error! Please Try Later";
	
	// Greeting Messages
	public static final String WELCOME_MESSAGE = "Welcome to Demo Bank! What would you like to do?";
	public static final String ADD_TRAN_MESSAGE = "Is there anything else you'd like to do?";
	public static final String FAIRWELL_MESSAGE = "Thank you for banking with Demo Bank.\nHave a nice day!";

	// Validation Messages
	public static final String ERROR_TAG = "ERROR! ";
	public static final String INVALID_USER_ACTION = "Invalid Action! Please Provide a Valid Service Code";
	public static final String INVALID_TRAN_DETAILS = "Invalid Transaction Details!";
	public static final String INVALID_DATE_FORMAT = "Invalid Date! Date should be in YYYYMMdd format";
	public static final String INVALID_TRAN_TYPE = "Invalid Transaction Type! Type should be D for deposit, W for withdrawal, case insensitive";
	public static final String INVALID_AMOUNT = "Invalid amount! Amount must be greater than zero, decimals are allowed up to 2 decimal places";
	public static final String INVALID_INTEREST_RULE_DETAILS = "Invalid Interest Rule Details!";
	public static final String INVALID_INTEREST_RATE = "Invalid Interest Rate! Interest rate should be greater than 0 and less than 100. Decimals are allowed up to 2 decimal places";
	public static final String INVALID_PRINT_DETAILS = "Invalid Statement Print Details!";
	public static final String INVALID_ACCOUNT_NO = "Invalid Account No.!";
	public static final String INVALID_DATE_YYYYMM_FORMAT = "Invalid Date! Date should be in YYYYMM format";

	// Input Messages
	public static final String TRANSACTION_INPUT_MESSAGE = "Please enter transaction details in <Date> <Account> <Type> <Amount> format\n(or enter B to go back to main menu):";
	public static final String INTEREST_INPUT_MESSAGE = "Please enter interest rules details in <Date> <RuleId> <Rate in %> format\n(or enter B to go back to main menu):";
	public static final String STATEMENT_PRINT_INPUT_MESSAGE = "Please enter account and month to generate the statement <Account> <Year><Month>\n(or enter B to go back to main menu):";

	// Functional Messages
	public static final String NO_BALANCE_FOR_W_MESSAGE = "Input Amount shouldn't not be greater than Account Available Balance!";
	public static final String NO_INTEREST_RULE_MESSAGE = "No Interest Rule is Defined!";
}