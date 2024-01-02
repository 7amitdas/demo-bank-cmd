package com.demobank.app.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

import com.demobank.app.CommonMessages;
import com.demobank.app.CommonUtil;
import com.demobank.app.model.AccountTransaction;
import com.demobank.app.model.InterestRate;
import com.demobank.app.model.TransactionType;

public class InterestRateService {

	private static Map<String, InterestRate> interestRuleList = new TreeMap<>();

	public Boolean addOrUpdateInterestRule(LocalDate intRateEffectiveDate, String interestRuleId,
			BigDecimal interestRate) {
		interestRuleList.put(CommonUtil.toDateString(intRateEffectiveDate),
				new InterestRate(intRateEffectiveDate, interestRuleId, interestRate));
		return true;
	}

	public void printDatewiseInterestRules() {
		if (interestRuleList != null && !interestRuleList.isEmpty()) {
			Collections.sort(new ArrayList<>(interestRuleList.keySet()), (i1, i2) -> i2.equalsIgnoreCase(i1) ? 0 : 1);
			System.out.println("Interest rules:");
			System.out.println("| Date     | RuleId | Rate (%) |");
			for (Map.Entry<String, InterestRate> entry : interestRuleList.entrySet()) {
				InterestRate val = entry.getValue();
				System.out.println("| " + CommonUtil.toDateString(val.getIntRateEffectiveDate()) + " | "
						+ CommonUtil.padRight(val.getIntRateRuleId(), 6, " ") + " | "
						+ CommonUtil.padRight(val.getInterestRate().toString(), 8, " ") + " |");
			}
			System.out.println();
		} else {
			System.out.println(CommonMessages.ERROR_TAG + CommonMessages.NO_INTEREST_RULE_MESSAGE);
		}
	}

	public static BigDecimal getInterestRateAsOnDate(LocalDate asOnDate) {
		if (interestRuleList != null && !interestRuleList.isEmpty()) {
			List<String> interestRateDateList = new ArrayList<>(interestRuleList.keySet());
			Collections.sort(interestRateDateList, (i1, i2) -> i2.equalsIgnoreCase(i1) ? 0 : 1);
			LocalDate val = interestRateDateList.stream().map(d -> CommonUtil.getLocalDateFromString(d))
					.filter(d -> d.compareTo(asOnDate) <= 0).sorted((d1, d2) -> d2.compareTo(d1)).findFirst()
					.orElse(null);
			if (val != null) {
				return interestRuleList.get(CommonUtil.toDateString(val)).getInterestRate();
			}
		}
		return BigDecimal.ZERO;
	}

	public static Long getNoOfDaysBetweenDates(LocalDate startDate, LocalDate endDate) {
		return ChronoUnit.DAYS.between(startDate, endDate);
	}

	public static BigDecimal applyYearlylyInterest(BigDecimal eodBalance, final LocalDate intCalcStartDate,
			final LocalDate intCalcEndDate, LocalDate distinctTransactionDate) {
		BigDecimal finalBal = BigDecimal.ZERO;
//		System.out.println("eodBalance=>" + eodBalance + ", intCalcStartDate=>" + intCalcStartDate + ", intCalcEndDate=>"
//				+ intCalcEndDate + ", distinctTransactionDate=>" + distinctTransactionDate + "#");
		if (interestRuleList != null && !interestRuleList.isEmpty()) {
			List<LocalDate> interestRateDateList = new ArrayList<>(interestRuleList.keySet()).stream()
					.map(d -> CommonUtil.getLocalDateFromString(d))
					.filter(t -> t.compareTo(intCalcStartDate) >= 0 && t.compareTo(intCalcEndDate) <= 0)
					.sorted((d1, d2) -> d1.compareTo(d2)).collect(Collectors.toList());
			BigDecimal intRateAsOnStartOfMonth = getInterestRateAsOnDate(intCalcStartDate.minusDays(1));
			Long noOfIntCalcDays = 0l;
			LocalDate intApplyStartDate = intCalcStartDate;
			LocalDate intApplyEndDate = intCalcEndDate;
			if (interestRateDateList != null && !interestRateDateList.isEmpty()) {
				for (int i = 0; i < interestRateDateList.size(); i++) {
					LocalDate distinctIntDate = interestRateDateList.get(i);
					if (distinctIntDate.compareTo(intApplyStartDate) != 0) {
						intApplyStartDate = intCalcStartDate;
						intApplyEndDate = distinctIntDate.minusDays(1);
						noOfIntCalcDays = getNoOfDaysBetweenDates(intApplyStartDate, intApplyEndDate) + 1;
						finalBal = finalBal
								.add(calculateYearlyInterest(eodBalance, intRateAsOnStartOfMonth, noOfIntCalcDays));
					}
					intApplyStartDate = distinctIntDate;
					if ((i + 1) < interestRateDateList.size()) {
						intApplyEndDate = interestRateDateList.get(i + 1).minusDays(1);
					} else {
						intApplyEndDate = intCalcEndDate;
					}
					BigDecimal intRateAsOnDate = getInterestRateAsOnDate(distinctIntDate);
					noOfIntCalcDays = getNoOfDaysBetweenDates(intApplyStartDate, intApplyEndDate) + 1;
					finalBal = finalBal.add(calculateYearlyInterest(eodBalance, intRateAsOnDate, noOfIntCalcDays));
					if ((i + 1) < interestRateDateList.size()) {
						intApplyStartDate = interestRateDateList.get(i + 1);
					} else {
						intApplyStartDate = intCalcEndDate.plusDays(1);
					}
				}
			} else {
				noOfIntCalcDays = getNoOfDaysBetweenDates(intCalcStartDate, intCalcEndDate) + 1;
				finalBal = calculateYearlyInterest(eodBalance, intRateAsOnStartOfMonth, noOfIntCalcDays);
			}
			// System.out.println("finalBal=>" + finalBal + "#");
			return finalBal;
		}
		return BigDecimal.ZERO;
	}

	public static BigDecimal calculateDailyInterest(BigDecimal interestAmountYearly) {
		return interestAmountYearly.divide(new BigDecimal("365"), 2, RoundingMode.HALF_UP);
	}

	public static BigDecimal calculateYearlyInterest(BigDecimal amount, BigDecimal intRate, Long getNoOfIntCalcDays) {
		return (amount.multiply(intRate).multiply(new BigDecimal(getNoOfIntCalcDays)))
				.divide(new BigDecimal("100"), 2, RoundingMode.HALF_UP).setScale(2, RoundingMode.HALF_UP);
	}

	public static void printCalculatedInterestAmountForStatement(String accountNo,
			List<AccountTransaction> accTransactionList, LocalDate startOfMonth, LocalDate endOfMonth,
			BigDecimal accountBalance) {
		BigDecimal calcInterestAmount = BigDecimal.ZERO;
		List<LocalDate> distinctDateWithTransactions = accTransactionList.stream().map(t -> t.getTransactionDate())
				.distinct().collect(Collectors.toList());
		LocalDate intCalcStartDate = startOfMonth;
		LocalDate intCalcEndDate = endOfMonth;
		BigDecimal totalYearlyIntAmount = BigDecimal.ZERO;

		for (int i = 0; i < distinctDateWithTransactions.size(); i++) {
			LocalDate distinctTransactionDate = distinctDateWithTransactions.get(i);
			if (distinctTransactionDate.compareTo(startOfMonth) == 0) {
				if ((i + 1) < distinctDateWithTransactions.size()) {
					intCalcStartDate = distinctTransactionDate;
					intCalcEndDate = distinctDateWithTransactions.get(i + 1).minusDays(1);
				} else {
					intCalcEndDate = endOfMonth;
				}
			} else {
				intCalcStartDate = distinctTransactionDate;
				if ((i + 1) < distinctDateWithTransactions.size()) {
					intCalcEndDate = distinctDateWithTransactions.get(i + 1).minusDays(1);
				} else {
					intCalcEndDate = endOfMonth;
				}
			}
			BigDecimal eodBalance = TransactionService.getAsOnDateAccountBalance(accountNo, distinctTransactionDate);
			totalYearlyIntAmount = totalYearlyIntAmount
					.add(applyYearlylyInterest(eodBalance, intCalcStartDate, intCalcEndDate, distinctTransactionDate))
					.setScale(2, RoundingMode.HALF_UP);
		}

		calcInterestAmount = calculateDailyInterest(totalYearlyIntAmount);
		accountBalance = accountBalance.add(calcInterestAmount).setScale(2, RoundingMode.HALF_UP);
		System.out.println("| " + CommonUtil.toDateString(endOfMonth) + " | " + CommonUtil.padRight("", 12, " ") + " | "
				+ TransactionType.I_TAG.transactionType + "    | "
				+ CommonUtil.padRight(calcInterestAmount.toString(), 16, " ") + " | "
				+ CommonUtil.padRight(accountBalance.toString(), 16, " ") + " |");
		System.out.println();
	}
}