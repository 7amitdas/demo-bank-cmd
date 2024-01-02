package com.demobank.app.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class InterestRate {

	/* Interest Rate Effective Date */
	private LocalDate intRateEffectiveDate;

	/* Interest Rate Rule Id */
	private String intRateRuleId;

	/* Interest Rate */
	private BigDecimal interestRate;

	public InterestRate(LocalDate intRateEffectiveDate, String intRateRuleId, BigDecimal interestRate) {
		this.intRateEffectiveDate = intRateEffectiveDate;
		this.intRateRuleId = intRateRuleId;
		this.interestRate = interestRate;
	}

	/**
	 * Get Interest Rate Effective Date
	 * 
	 * @return LocalDate intRateEffectiveDate
	 */
	public LocalDate getIntRateEffectiveDate() {
		return intRateEffectiveDate;
	}

	/**
	 * Get Interest Rate Rule Id
	 * 
	 * @return String intRateRuleId
	 */
	public String getIntRateRuleId() {
		return intRateRuleId;
	}

	/**
	 * Set Interest Rate Rule Id
	 * 
	 * @param intRateRuleId the intRateRuleId to set
	 */
	public void setIntRateRuleId(String intRateRuleId) {
		this.intRateRuleId = intRateRuleId;
	}

	/**
	 * Get Interest Rate
	 * 
	 * @return BigDecimal interestRate
	 */
	public BigDecimal getInterestRate() {
		return interestRate;
	}

	/**
	 * Set Interest Rate
	 * 
	 * @param interestRate the interestRate to set
	 */
	public void setInterestRate(BigDecimal interestRate) {
		this.interestRate = interestRate;
	}
}