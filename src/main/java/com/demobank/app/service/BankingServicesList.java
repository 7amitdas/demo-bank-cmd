package com.demobank.app.service;

public enum BankingServicesList {
	T_TAG("T", "Input transactions"), I_TAG("I", "Define interest rules"), P_TAG("P", "Print statement"),
	Q_TAG("Q", "Quit");

	/* Bank Service Code */
	public final String serviceCode;

	/* Bank Service Name */
	public final String serviceName;

	/**
	 * @param serviceType        Bank Service Code
	 * @param serviceDescription Bank Service Name
	 */
	private BankingServicesList(String serviceType, String serviceDescription) {
		this.serviceCode = serviceType;
		this.serviceName = serviceDescription;
	}

	/**
	 * public Method to Print all the available Banking Services
	 * 
	 */
	public static void printServices() {
		for (int i = 0; i < BankingServicesList.values().length; i++) {
			System.out.println("[" + BankingServicesList.values()[i].serviceCode + "] "
					+ BankingServicesList.values()[i].serviceName);
		}
	}

	/**
	 * public Method to find Service by Service Code
	 * 
	 * @param serviceCode Banking Service Code
	 * @return True if Service Code is found otherwise false
	 */
	public static Boolean findByServiceCode(String serviceCode) {
		for (int i = 0; i < BankingServicesList.values().length; i++) {
			if (BankingServicesList.values()[i].serviceCode.equalsIgnoreCase(serviceCode)) {
				return true;
			}
		}
		return false;
	}
}