package com.capgemini.hackathon.automation.model;


import java.util.HashMap;



public class CustomerTxnModel {
	private Integer customerId;
	private HashMap<String, HashMap<String, Double>> beforeDeMoneMonthlyCRAndDRTxnAmt;
	private HashMap<String, HashMap<String, Double>> aftereDeMoneMonthlyCRAndDRTxnAmt;
	private boolean isFraud;
	private String commentsForFraud;
	private Double dailyMaxDeposit;
	private Double salary =0.0;
	private String salaryCategory;
	public Integer getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}
	
	public boolean isFraud() {
		return isFraud;
	}
	public HashMap<String, HashMap<String, Double>> getBeforeDeMoneMonthlyCRAndDRTxnAmt() {
		return beforeDeMoneMonthlyCRAndDRTxnAmt;
	}
	public void setBeforeDeMoneMonthlyCRAndDRTxnAmt(
			HashMap<String, HashMap<String, Double>> beforeDeMoneMonthlyCRAndDRTxnAmt) {
		this.beforeDeMoneMonthlyCRAndDRTxnAmt = beforeDeMoneMonthlyCRAndDRTxnAmt;
	}
	public HashMap<String, HashMap<String, Double>> getAftereDeMoneMonthlyCRAndDRTxnAmt() {
		return aftereDeMoneMonthlyCRAndDRTxnAmt;
	}
	public void setAftereDeMoneMonthlyCRAndDRTxnAmt(
			HashMap<String, HashMap<String, Double>> aftereDeMoneMonthlyCRAndDRTxnAmt) {
		this.aftereDeMoneMonthlyCRAndDRTxnAmt = aftereDeMoneMonthlyCRAndDRTxnAmt;
	}
	public void setFraud(boolean isFraud) {
		this.isFraud = isFraud;
	}
	public String getCommentsForFraud() {
		return commentsForFraud;
	}
	public void setCommentsForFraud(String commentsForFraud) {
		this.commentsForFraud = commentsForFraud;
	}
	public Double getDailyMaxDeposit() {
		return dailyMaxDeposit;
	}
	public void setDailyMaxDeposit(Double dailyMaxDeposit) {
		this.dailyMaxDeposit = dailyMaxDeposit;
	}
	public Double getSalary() {
		return salary;
	}
	public void setSalary(Double salary) {
		this.salary = salary;
	}
	public String getSalaryCategory() {
		return salaryCategory;
	}
	public void setSalaryCategory(String salaryCategory) {
		this.salaryCategory = salaryCategory;
	}

	
}
