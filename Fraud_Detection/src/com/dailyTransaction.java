package com;

import java.io.Serializable;
import java.util.Date;
import java.util.Hashtable;

public class dailyTransaction implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5286470665015263996L;
	
	private Integer creditCount;
	private double totalCredits;
	
	private Integer debitCount;
	private double totalDebits;	
	
	public Integer getCreditCount() {
		return creditCount;
	}
	public void setCreditCount(Integer creditCount) {
		this.creditCount = creditCount;
	}
	public double getTotalDebits() {
		return totalDebits;
	}
	public void setTotalDebits(double totalDebits) {
		this.totalDebits = totalDebits;
	}
	public Integer getDebitCount() {
		return debitCount;
	}
	public void setDebitCount(Integer debitCount) {
		this.debitCount = debitCount;
	}
	public double getTotalCredits() {
		return totalCredits;
	}
	public void setTotalCredits(double totalCredits) {
		this.totalCredits = totalCredits;
	}	

}
