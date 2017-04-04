package com.capgemini.hackathon.automation.model;


public class Employee {
	private Integer employeeId;
	private String employeeName;
	private Double salary=null;
	private Integer noOfBankDR=0;
	private Integer noOfBankCR=0;
	private Double creditAmt=0.0;
	private Double debitAmt=0.0;
	private String summary;
	private Double loanAmount=0.0;
	public Integer getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(Integer employeeId) {
		this.employeeId = employeeId;
	}
	public String getEmployeeName() {
		return employeeName;
	}
	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}
	public Double getSalary() {
		return salary;
	}
	public void setSalary(Double salary) {
		this.salary = salary;
	}
	public Integer getNoOfBankDR() {
		return noOfBankDR;
	}
	public void setNoOfBankDR(Integer noOfBankDR) {
		this.noOfBankDR = noOfBankDR;
	}
	public Integer getNoOfBankCR() {
		return noOfBankCR;
	}
	public void setNoOfBankCR(Integer noOfBankCR) {
		this.noOfBankCR = noOfBankCR;
	}
	public Double getCreditAmt() {
		return creditAmt;
	}
	public void setCreditAmt(Double creditAmt) {
		this.creditAmt = creditAmt;
	}
	public Double getDebitAmt() {
		return debitAmt;
	}
	public void setDebitAmt(Double debitAmt) {
		this.debitAmt = debitAmt;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public Double getLoanAmount() {
		return loanAmount;
	}
	public void setLoanAmount(Double loanAmount) {
		this.loanAmount = loanAmount;
	}
	
	
}
