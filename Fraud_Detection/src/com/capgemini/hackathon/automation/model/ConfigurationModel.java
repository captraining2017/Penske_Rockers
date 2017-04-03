package com.capgemini.hackathon.automation.model;

import java.util.List;

public class ConfigurationModel {
	private String masterEmployeeFilePath;
	private String customerTxnDataFilePath;
	private String delimiter;
	private Long dataSize;
	private String sampleDataStartDate;
	private String sampleDataEndDate;
	private String demonetizationThresoldDate;
	private Double maxLowSalaryRange;
	private Double maxMediumSalaryRange;
	private Double maxHighSalaryRange;
	private List<Integer> months;
	private List<Integer>years;
	
	private List<Integer> lowSalaryTxnNoDRAndCRBefore;
	private List<Integer> mediumSalaryTxnNoDRAndCRBefore;
	private List<Integer> highSalaryTxnNoDRAndCRBefore;
	private List<Integer> lowSalaryTxnNoDRAndCRAfter;
	private List<Integer> mediumSalaryTxnNoDRAndCRAfter;
	private List<Integer> highSalaryTxnNoDRAndCRAfter;
	private List<String> txnTypeList;
	
	private List<Double> lowSalaryCRBefore;
	private List<Double> mediumSalaryCRBefore;
	private List<Double> highSalaryCRBefore;
	
	private List<Double> lowSalaryDRBefore;
	private List<Double> mediumSalaryDRBefore;
	private List<Double> highSalaryDRBefore;
	
	private List<Double> lowSalaryDRAfter;
	private List<Double> mediumSalaryDRAfter;
	private List<Double> highSalaryDRAfter;
	
	private List<Double> demonitizationCR;
	
	private Float lowSalaryCustomerPercent;
	private Float mediumSalaryCustomerPercent;
	private Float highSalaryCustomerPercent;
	
	private Integer deMonetizationCRNoTimeIncr;
	private Integer deMonetizationDRNoTimeIncr;
	public String getMasterEmployeeFilePath() {
		return masterEmployeeFilePath;
	}
	public void setMasterEmployeeFilePath(String masterEmployeeFilePath) {
		this.masterEmployeeFilePath = masterEmployeeFilePath;
	}
	public String getCustomerTxnDataFilePath() {
		return customerTxnDataFilePath;
	}
	public void setCustomerTxnDataFilePath(String customerTxnDataFilePath) {
		this.customerTxnDataFilePath = customerTxnDataFilePath;
	}
	public List<String> getTxnTypeList() {
		return txnTypeList;
	}
	public void setTxnTypeList(List<String> txnTypeList) {
		this.txnTypeList = txnTypeList;
	}
	public String getDelimiter() {
		return delimiter;
	}
	public void setDelimiter(String delimiter) {
		this.delimiter = delimiter;
	}
	public Long getDataSize() {
		return dataSize;
	}
	public void setDataSize(Long dataSize) {
		this.dataSize = dataSize;
	}
	public String getSampleDataStartDate() {
		return sampleDataStartDate;
	}
	public void setSampleDataStartDate(String sampleDataStartDate) {
		this.sampleDataStartDate = sampleDataStartDate;
	}
	public String getSampleDataEndDate() {
		return sampleDataEndDate;
	}
	public void setSampleDataEndDate(String sampleDataEndDate) {
		this.sampleDataEndDate = sampleDataEndDate;
	}
	public String getDemonetizationThresoldDate() {
		return demonetizationThresoldDate;
	}
	public void setDemonetizationThresoldDate(String demonetizationThresoldDate) {
		this.demonetizationThresoldDate = demonetizationThresoldDate;
	}
	public Double getMaxLowSalaryRange() {
		return maxLowSalaryRange;
	}
	public void setMaxLowSalaryRange(Double maxLowSalaryRange) {
		this.maxLowSalaryRange = maxLowSalaryRange;
	}
	public Double getMaxMediumSalaryRange() {
		return maxMediumSalaryRange;
	}
	public void setMaxMediumSalaryRange(Double maxMediumSalaryRange) {
		this.maxMediumSalaryRange = maxMediumSalaryRange;
	}
	public Double getMaxHighSalaryRange() {
		return maxHighSalaryRange;
	}
	public void setMaxHighSalaryRange(Double maxHighSalaryRange) {
		this.maxHighSalaryRange = maxHighSalaryRange;
	}
	public List<Integer> getMonths() {
		return months;
	}
	public void setMonths(List<Integer> months) {
		this.months = months;
	}
	public List<Integer> getYears() {
		return years;
	}
	public void setYears(List<Integer> years) {
		this.years = years;
	}
	
	
	public List<Double> getLowSalaryCRBefore() {
		return lowSalaryCRBefore;
	}
	public void setLowSalaryCRBefore(List<Double> lowSalaryCRBefore) {
		this.lowSalaryCRBefore = lowSalaryCRBefore;
	}
	public List<Double> getMediumSalaryCRBefore() {
		return mediumSalaryCRBefore;
	}
	public void setMediumSalaryCRBefore(List<Double> mediumSalaryCRBefore) {
		this.mediumSalaryCRBefore = mediumSalaryCRBefore;
	}
	
	public List<Double> getHighSalaryCRBefore() {
		return highSalaryCRBefore;
	}
	public void setHighSalaryCRBefore(List<Double> highSalaryCRBefore) {
		this.highSalaryCRBefore = highSalaryCRBefore;
	}
	public List<Double> getLowSalaryDRBefore() {
		return lowSalaryDRBefore;
	}
	public void setLowSalaryDRBefore(List<Double> lowSalaryDRBefore) {
		this.lowSalaryDRBefore = lowSalaryDRBefore;
	}
	public List<Double> getMediumSalaryDRBefore() {
		return mediumSalaryDRBefore;
	}
	public void setMediumSalaryDRBefore(List<Double> mediumSalaryDRBefore) {
		this.mediumSalaryDRBefore = mediumSalaryDRBefore;
	}
	public List<Double> getHighSalaryDRBefore() {
		return highSalaryDRBefore;
	}
	public void setHighSalaryDRBefore(List<Double> highSalaryDRBefore) {
		this.highSalaryDRBefore = highSalaryDRBefore;
	}
	public List<Double> getLowSalaryDRAfter() {
		return lowSalaryDRAfter;
	}
	public void setLowSalaryDRAfter(List<Double> lowSalaryDRAfter) {
		this.lowSalaryDRAfter = lowSalaryDRAfter;
	}
	public List<Double> getMediumSalaryDRAfter() {
		return mediumSalaryDRAfter;
	}
	public void setMediumSalaryDRAfter(List<Double> mediumSalaryDRAfter) {
		this.mediumSalaryDRAfter = mediumSalaryDRAfter;
	}
	public List<Double> getHighSalaryDRAfter() {
		return highSalaryDRAfter;
	}
	public void setHighSalaryDRAfter(List<Double> highSalaryDRAfter) {
		this.highSalaryDRAfter = highSalaryDRAfter;
	}
	
		
	public List<Double> getDemonitizationCR() {
		return demonitizationCR;
	}
	public void setDemonitizationCR(List<Double> demonitizationCR) {
		this.demonitizationCR = demonitizationCR;
	}
	public List<Integer> getLowSalaryTxnNoDRAndCRBefore() {
		return lowSalaryTxnNoDRAndCRBefore;
	}
	public void setLowSalaryTxnNoDRAndCRBefore(
			List<Integer> lowSalaryTxnNoDRAndCRBefore) {
		this.lowSalaryTxnNoDRAndCRBefore = lowSalaryTxnNoDRAndCRBefore;
	}
	public List<Integer> getMediumSalaryTxnNoDRAndCRBefore() {
		return mediumSalaryTxnNoDRAndCRBefore;
	}
	public void setMediumSalaryTxnNoDRAndCRBefore(
			List<Integer> mediumSalaryTxnNoDRAndCRBefore) {
		this.mediumSalaryTxnNoDRAndCRBefore = mediumSalaryTxnNoDRAndCRBefore;
	}
	public List<Integer> getHighSalaryTxnNoDRAndCRBefore() {
		return highSalaryTxnNoDRAndCRBefore;
	}
	public void setHighSalaryTxnNoDRAndCRBefore(
			List<Integer> highSalaryTxnNoDRAndCRBefore) {
		this.highSalaryTxnNoDRAndCRBefore = highSalaryTxnNoDRAndCRBefore;
	}
	
	
	public List<Integer> getLowSalaryTxnNoDRAndCRAfter() {
		return lowSalaryTxnNoDRAndCRAfter;
	}
	public void setLowSalaryTxnNoDRAndCRAfter(
			List<Integer> lowSalaryTxnNoDRAndCRAfter) {
		this.lowSalaryTxnNoDRAndCRAfter = lowSalaryTxnNoDRAndCRAfter;
	}
	public List<Integer> getMediumSalaryTxnNoDRAndCRAfter() {
		return mediumSalaryTxnNoDRAndCRAfter;
	}
	public void setMediumSalaryTxnNoDRAndCRAfter(
			List<Integer> mediumSalaryTxnNoDRAndCRAfter) {
		this.mediumSalaryTxnNoDRAndCRAfter = mediumSalaryTxnNoDRAndCRAfter;
	}
	public List<Integer> getHighSalaryTxnNoDRAndCRAfter() {
		return highSalaryTxnNoDRAndCRAfter;
	}
	public void setHighSalaryTxnNoDRAndCRAfter(
			List<Integer> highSalaryTxnNoDRAndCRAfter) {
		this.highSalaryTxnNoDRAndCRAfter = highSalaryTxnNoDRAndCRAfter;
	}
	public Float getLowSalaryCustomerPercent() {
		return lowSalaryCustomerPercent;
	}
	public void setLowSalaryCustomerPercent(Float lowSalaryCustomerPercent) {
		this.lowSalaryCustomerPercent = lowSalaryCustomerPercent;
	}
	public Float getMediumSalaryCustomerPercent() {
		return mediumSalaryCustomerPercent;
	}
	public void setMediumSalaryCustomerPercent(Float mediumSalaryCustomerPercent) {
		this.mediumSalaryCustomerPercent = mediumSalaryCustomerPercent;
	}
	public Float getHighSalaryCustomerPercent() {
		return highSalaryCustomerPercent;
	}
	public void setHighSalaryCustomerPercent(Float highSalaryCustomerPercent) {
		this.highSalaryCustomerPercent = highSalaryCustomerPercent;
	}
	public Integer getDeMonetizationCRNoTimeIncr() {
		return deMonetizationCRNoTimeIncr;
	}
	public void setDeMonetizationCRNoTimeIncr(Integer deMonetizationCRNoTimeIncr) {
		this.deMonetizationCRNoTimeIncr = deMonetizationCRNoTimeIncr;
	}
	public Integer getDeMonetizationDRNoTimeIncr() {
		return deMonetizationDRNoTimeIncr;
	}
	public void setDeMonetizationDRNoTimeIncr(Integer deMonetizationDRNoTimeIncr) {
		this.deMonetizationDRNoTimeIncr = deMonetizationDRNoTimeIncr;
	}
	
	
	
	
}
