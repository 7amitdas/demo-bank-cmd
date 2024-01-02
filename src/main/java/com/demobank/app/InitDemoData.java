package com.demobank.app;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.demobank.app.service.AccountService;
import com.demobank.app.service.InterestRateService;
import com.demobank.app.service.TransactionService;

public class InitDemoData {

	public InitDemoData() {
		InterestRateService i = new InterestRateService();
		i.addOrUpdateInterestRule(LocalDate.of(2023, 1, 1), "RULE01", new BigDecimal("1.95"));
		i.addOrUpdateInterestRule(LocalDate.of(2023, 6, 20), "RULE02", new BigDecimal("1.90"));
		i.addOrUpdateInterestRule(LocalDate.of(2023, 6, 15), "RULE03", new BigDecimal("2.20"));
		AccountService a = new AccountService();
		a.addAccount("A123");
		TransactionService t1 = new TransactionService();
//		t1.processTransaction(LocalDate.of(2023, 5, 31), "A123", "D", new BigDecimal("100.00"));
//		t1.processTransaction(LocalDate.of(2023, 6, 1), "A123", "D", new BigDecimal("150.00"));
//		t1.processTransaction(LocalDate.of(2023, 6, 26), "A123", "W", new BigDecimal("20.00"));
//		t1.processTransaction(LocalDate.of(2023, 6, 26), "A123", "W", new BigDecimal("100.00"));
		t1.processTransaction(LocalDate.of(2023, 6, 1), "A123", "D", new BigDecimal("1000.00"));
		t1.processTransaction(LocalDate.of(2023, 6, 1), "A123", "W", new BigDecimal("300.00"));
		t1.processTransaction(LocalDate.of(2023, 6, 2), "A123", "D", new BigDecimal("1000.00"));
		t1.processTransaction(LocalDate.of(2023, 6, 9), "A123", "W", new BigDecimal("100.00"));
		t1.processTransaction(LocalDate.of(2023, 6, 15), "A123", "D", new BigDecimal("450.00"));
		t1.processTransaction(LocalDate.of(2023, 6, 25), "A123", "W", new BigDecimal("255.00"));
		t1.processTransaction(LocalDate.of(2023, 6, 25), "A123", "D", new BigDecimal("1000.00"));
		t1.processTransaction(LocalDate.of(2023, 7, 1), "A123", "D", new BigDecimal("1000.00"));
		t1.processTransaction(LocalDate.of(2023, 7, 1), "A123", "W", new BigDecimal("1200.00"));
		t1.processTransaction(LocalDate.of(2023, 10, 11), "A123", "D", new BigDecimal("1000.00"));
	}
}