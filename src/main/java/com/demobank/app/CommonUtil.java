package com.demobank.app;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoField;
import java.util.List;

public class CommonUtil {

	public static final String DEFAULT_DATE_FORMAT = "yyyyMMdd";
	public static final String YYYYMM_DATE_FORMAT = "yyyyMM";
	public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(DEFAULT_DATE_FORMAT);
	public static final DateTimeFormatter DATE_FORMATTER_YYYYMM = new DateTimeFormatterBuilder()
			.appendPattern(YYYYMM_DATE_FORMAT).parseDefaulting(ChronoField.DAY_OF_MONTH, 1).toFormatter();

	public static boolean isBlank(String str) {
		if (str == null || str.trim().equalsIgnoreCase("")) {
			return true;
		}
		return false;
	}

	public static boolean isNotBlank(String str) {
		return !isBlank(str);
	}

	public static boolean isEmpty(List<?> str) {
		return (str == null || str.isEmpty());
	}
	
	public static LocalDate getLocalDateFromString(String dateString) {
		try {
			return LocalDate.parse(dateString, DATE_FORMATTER);
		} catch (DateTimeParseException e) {
			return null;
		}
	}

	public static LocalDate getLocalDateFromStringYYYYMM(String dateString) {
		try {
			return LocalDate.parse(dateString, DATE_FORMATTER_YYYYMM);
		} catch (DateTimeParseException e) {
			return null;
		}
	}

	public static String toDateString(LocalDate date) {
		return date.format(DATE_FORMATTER);
	}

	public static String padLeft(String str, int size, String padStr) {
		return String.format("%" + size + "s", str).replace(" ", padStr);
	}

	public static String padRight(String str, int size, String padStr) {
		return String.format("%-" + size + "s", str).replace(" ", padStr);
	}
}