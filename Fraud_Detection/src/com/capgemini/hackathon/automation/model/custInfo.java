package com.capgemini.hackathon.automation.model;

import java.io.Serializable;

public class CustInfo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7897904673534191877L;	
	private String customerName;
	private double salary;
	private boolean isFraud;
	private String profileCategory;
	private double cashDebit;
	private double cashCredit;
	
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public double getSalary() {
		return salary;
	}
	public void setSalary(long salary) {
		this.salary = salary;
	}
	public boolean isFraud() {
		return isFraud;
	}
	public void setFraud(boolean isFraud) {
		this.isFraud = isFraud;
	}
	public String getProfileCategory() {
		return profileCategory;
	}
	public void setProfileCategory(String profileCategory) {
		this.profileCategory = profileCategory;
	}
	public double getCashDebit() {
		return cashDebit;
	}
	public void setCashDebit(double cashDebit) {
		this.cashDebit = cashDebit;
	}
	public double getCashCredit() {
		return cashCredit;
	}
	public void setCashCredit(double cashCredit) {
		this.cashCredit = cashCredit;
	}

}
