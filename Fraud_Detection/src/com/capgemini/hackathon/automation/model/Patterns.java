package com.capgemini.hackathon.automation.model;

import java.io.Serializable;

public class Patterns implements Serializable{
	
	private int lowCreditCount;	
	private int mediumCreditCount;	
	private int highCreditCount;
	

	private int lowDemontizationCreditCount;	
	private int mediumDemontizationCreditCount;	
	private int highDemontizationCreditCount;
	
	private long demontizationCrAmount;
	
	private long lowDemontizationCrAmount;
	private long mediumDemontizationCrAmount;
	private long highDemontizationCrAmount;
	
	public int getLowCreditCount() {
		return lowCreditCount;
	}
	public void setLowCreditCount(int lowCreditCount) {
		this.lowCreditCount = lowCreditCount;
	}
	public int getMediumCreditCount() {
		return mediumCreditCount;
	}
	public void setMediumCreditCount(int mediumCreditCount) {
		this.mediumCreditCount = mediumCreditCount;
	}
	public int getHighCreditCount() {
		return highCreditCount;
	}
	public void setHighCreditCount(int highCreditCount) {
		this.highCreditCount = highCreditCount;
	}
	public int getLowDemontizationCreditCount() {
		return lowDemontizationCreditCount;
	}
	public void setLowDemontizationCreditCount(int lowDemontizationCreditCount) {
		this.lowDemontizationCreditCount = lowDemontizationCreditCount;
	}
	public int getMediumDemontizationCreditCount() {
		return mediumDemontizationCreditCount;
	}
	public void setMediumDemontizationCreditCount(
			int mediumDemontizationCreditCount) {
		this.mediumDemontizationCreditCount = mediumDemontizationCreditCount;
	}
	public int getHighDemontizationCreditCount() {
		return highDemontizationCreditCount;
	}
	public void setHighDemontizationCreditCount(int highDemontizationCreditCount) {
		this.highDemontizationCreditCount = highDemontizationCreditCount;
	}
	public double getDemontizationCrAmount() {
		return demontizationCrAmount;
	}
	public void setDemontizationCrAmount(long demontizationCrAmount) {
		this.demontizationCrAmount = demontizationCrAmount;
	}
	public double getLowDemontizationCrAmount() {
		return lowDemontizationCrAmount;
	}
	public void setLowDemontizationCrAmount(long lowDemontizationCrAmount) {
		this.lowDemontizationCrAmount = lowDemontizationCrAmount;
	}
	public double getMediumDemontizationCrAmount() {
		return mediumDemontizationCrAmount;
	}
	public void setMediumDemontizationCrAmount(long mediumDemontizationCrAmount) {
		this.mediumDemontizationCrAmount = mediumDemontizationCrAmount;
	}
	public double getHighDemontizationCrAmount() {
		return highDemontizationCrAmount;
	}
	public void setHighDemontizationCrAmount(long highDemontizationCrAmount) {
		this.highDemontizationCrAmount = highDemontizationCrAmount;
	}
	

}
