package com.capgemini.hackathon.automation.model;

import java.io.Serializable;

public class CustomerCategory implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8001211625079642304L;
			
	private long lowCustCreditLimit;
	private long mediumCustCreditLimit;
	private long highCustCreditLimit;
	
	public long getLowCustCreditLimit() {
		return lowCustCreditLimit;
	}
	public void setLowCustCreditLimit(long lowCustCreditLimit) {
		this.lowCustCreditLimit = lowCustCreditLimit;
	}
	public long getMediumCustCreditLimit() {
		return mediumCustCreditLimit;
	}
	public void setMediumCustCreditLimit(long mediumCustCreditLimit) {
		this.mediumCustCreditLimit = mediumCustCreditLimit;
	}
	public long getHighCustCreditLimit() {
		return highCustCreditLimit;
	}
	public void setHighCustCreditLimit(long highCustCreditLimit) {
		this.highCustCreditLimit = highCustCreditLimit;
	}
	

}
