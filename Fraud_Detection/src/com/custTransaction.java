package com;

import java.io.Serializable;
import java.security.Timestamp;

public class custTransaction implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 14082343433908149L;
	
	private long transactionId;
	private Timestamp transTime;
	private double creditAmt;
	private double debitAmt;
	public long getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(long transactionId) {
		this.transactionId = transactionId;
	}
	public Timestamp getTransTime() {
		return transTime;
	}
	public void setTransTime(Timestamp transTime) {
		this.transTime = transTime;
	}
	public double getCreditAmt() {
		return creditAmt;
	}
	public void setCreditAmt(double creditAmt) {
		this.creditAmt = creditAmt;
	}
	public double getDebitAmt() {
		return debitAmt;
	}
	public void setDebitAmt(double debitAmt) {
		this.debitAmt = debitAmt;
	}
	

}
