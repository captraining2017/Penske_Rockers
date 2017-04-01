package com;

import java.io.Serializable;

public class customerCategory implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8001211625079642304L;
	
	private long lowSalaryCust;
	private long mediumSalaryCust;
	private long highSalaryCust;
	
	private Integer lowCustDebitLimit;
	private Integer mediumCustDebitLimit;
	private Integer highCustDebitLimit;
	private Integer lowCustCreditLimit;
	private Integer mediumCustCreditLimit;
	private Integer highCustCreditLimit;
	public long getLowSalaryCust() {
		return lowSalaryCust;
	}
	public void setLowSalaryCust(long lowSalaryCust) {
		this.lowSalaryCust = lowSalaryCust;
	}
	public long getMediumSalaryCust() {
		return mediumSalaryCust;
	}
	public void setMediumSalaryCust(long mediumSalaryCust) {
		this.mediumSalaryCust = mediumSalaryCust;
	}
	public long getHighSalaryCust() {
		return highSalaryCust;
	}
	public void setHighSalaryCust(long highSalaryCust) {
		this.highSalaryCust = highSalaryCust;
	}
	public Integer getLowCustDebitLimit() {
		return lowCustDebitLimit;
	}
	public void setLowCustDebitLimit(Integer lowCustDebitLimit) {
		this.lowCustDebitLimit = lowCustDebitLimit;
	}
	public Integer getMediumCustDebitLimit() {
		return mediumCustDebitLimit;
	}
	public void setMediumCustDebitLimit(Integer mediumCustDebitLimit) {
		this.mediumCustDebitLimit = mediumCustDebitLimit;
	}
	public Integer getHighCustDebitLimit() {
		return highCustDebitLimit;
	}
	public void setHighCustDebitLimit(Integer highCustDebitLimit) {
		this.highCustDebitLimit = highCustDebitLimit;
	}
	public Integer getLowCustCreditLimit() {
		return lowCustCreditLimit;
	}
	public void setLowCustCreditLimit(Integer lowCustCreditLimit) {
		this.lowCustCreditLimit = lowCustCreditLimit;
	}
	public Integer getMediumCustCreditLimit() {
		return mediumCustCreditLimit;
	}
	public void setMediumCustCreditLimit(Integer mediumCustCreditLimit) {
		this.mediumCustCreditLimit = mediumCustCreditLimit;
	}
	public Integer getHighCustCreditLimit() {
		return highCustCreditLimit;
	}
	public void setHighCustCreditLimit(Integer highCustCreditLimit) {
		this.highCustCreditLimit = highCustCreditLimit;
	}
	

}
