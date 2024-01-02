package com.demobank.app.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.demobank.app.service.AccountService;
import com.demobank.app.service.TransactionService;

public class AccountServiceTest {

	private AccountService targetClass = null;

	@BeforeEach
	public void setUp() {
		targetClass = new AccountService();
		targetClass.addAccount("S123");
		AccountService.updateAccountBalance("S123", new BigDecimal("100"));
		TransactionService t1 = new TransactionService();
		t1.processTransaction(LocalDate.of(2023, 6, 1), "S123", "D", new BigDecimal("1000.00"));
		t1.processTransaction(LocalDate.of(2023, 6, 1), "S123", "W", new BigDecimal("300.00"));
	}

	@Test
	public void testConstructor() {
		assertNotNull(targetClass);
	}

	@Test
	public void testAddAccount() {
		targetClass.addAccount("S124");
		assertEquals("S124", AccountService.getAccountList().get("S124").getAccountNo());
	}

	@Test
	public void testGetCustAccountByAccountNo() {
		assertEquals("S123", AccountService.getCustAccountByAccountNo("S123").getAccountNo());
	}

	@Test
	public void testUpdateAccountBalance() {
		AccountService.updateAccountBalance("S123", new BigDecimal("500.50"));
		assertEquals(new BigDecimal("500.50"), AccountService.getCustAccountByAccountNo("S123").getAccountBalance());
	}
	
	@Test
	public void testPrintAccountStatementByAccountNoAndDate() {
		targetClass.printAccountStatementByAccountNoAndDate("S123", LocalDate.of(2023, 06, 01));
	}
}