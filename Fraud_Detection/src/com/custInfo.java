package com;

import java.io.Serializable;

public class custInfo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7897904673534191877L;
	private long customerId;
	private String customerName;
	private double salary;
	public long getCustomerId() {
		return customerId;
	}
	public void setCustomerId(long customerId) {
		this.customerId = customerId;
	}
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

}
