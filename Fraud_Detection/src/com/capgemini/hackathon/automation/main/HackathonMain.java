package com.capgemini.hackathon.automation.main;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.capgemini.hackathon.automation.model.ConfigurationModel;
import com.capgemini.hackathon.automation.util.HugeDataSetCreationUtil;

public class HackathonMain {

	
	public static void main(String[] args) {
		
		HackathonMain hackthonObj = new HackathonMain();
		ConfigurationModel configModelObj =hackthonObj.loadProperties();
		HugeDataSetCreationUtil.generateSampleTxnData( configModelObj);
	}

	private ConfigurationModel loadProperties() {
		Properties prop = new Properties();
		InputStream input = null;
		ConfigurationModel configModelObj = null;
		try 
		{
			input = new FileInputStream("D:\\Hackathon\\Fraud_Detection\\bin\\conf\\fraudAlert.properties");
			prop.load(input);
			configModelObj=populateConfigModelObj(prop);
	 	} 
		catch (FileNotFoundException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (IOException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
		}
		return configModelObj;
	}
	
	private ConfigurationModel populateConfigModelObj(Properties prop){
		ConfigurationModel configObj = new ConfigurationModel();
		String masterEmployeeFilePath = prop.getProperty("FraudAlert_Master_Employee_File");
		String customerTxnDataFilePath = prop.getProperty("FraudAlert_Customer_Txn_Data_File");
		String delimiter = prop.getProperty("FraudAlert_Delimiter");
		Long dataSize = Long.valueOf(prop.getProperty("FraudAlert_DataSize"));
		String sampleDataStartDate = prop.getProperty("FraudAlert_StartDate");
		String sampleDataEndDate = prop.getProperty("FraudAlert_EndDate");
		String demonetizationDate = prop.getProperty("FraudAlert_DemonetizationDate");
		Double lowSalary=Double.valueOf(prop.getProperty("FraudAlert_LowSalary_Threshold"));
		Double mediumSalary=Double.valueOf(prop.getProperty("FraudAlert_MediumSalary_Threshold"));
		Double highSalary=Double.valueOf(prop.getProperty("FraudAlert_HighSalary_Threshold"));
		String months =prop.getProperty("FraudAlert_months");
		String years =prop.getProperty("FraudAlert_years");
		
		String lowSalaryTxnNoDRAndCRBefore= prop.getProperty("FraudAlert_LowSalary_Max_TXN_NO_DRAndCR_Before");
		String mediumSalaryTxnNoDRAndCRBefore= prop.getProperty("FraudAlert_MediumSalary_Max_TXN_NO_DRAndCR_Before");
		String highSalaryTxnNoDRAndCRBefore= prop.getProperty("FraudAlert_HighSalary_Max_TXN_NO_DRAndCR_Before");
		String lowSalaryDRTxnNoAndCRAfter= prop.getProperty("FraudAlert_LowSalary_Max_TXN_NO_DRAndCR_After");
		String mediumSalaryTxnNoDRAndCRAfter= prop.getProperty("FraudAlert_MediumSalary_Max_TXN_NO_DRAndCR_After");
		String highSalaryTxnNoDRAndCRAfter= prop.getProperty("FraudAlert_HighSalary_Max_TXN_NO_DRAndCR_After");
		String txnTypesArr []=prop.getProperty("FraudAlert_Transaction_Type").split(",");
		
		String lowSalaryCR []=prop.getProperty("FraudAlert_lowsalary_CR").split(",");
		String mediumSalaryCR []=prop.getProperty("FraudAlert_mediumsalary_CR").split(",");
		String highSalaryCR []=prop.getProperty("FraudAlert_highsalary_CR").split(",");
		String lowSalaryDR []=prop.getProperty("FraudAlert_lowsalary_DR").split(",");
		String mediumSalaryDR []=prop.getProperty("FraudAlert_mediumsalary_DR").split(",");
		String highSalaryDR []=prop.getProperty("FraudAlert_highsalary_DR").split(",");
		
		String lowSalaryDRAfter []=prop.getProperty("FraudAlert_lowsalary_DR").split(",");
		String mediumSalaryDRAfter []=prop.getProperty("FraudAlert_mediumsalary_DR").split(",");
		String highSalaryDRAfter []=prop.getProperty("FraudAlert_highsalary_DR").split(",");
		
		String deMonetizationCR [] =prop.getProperty("FraudAlert_Demonetization_CR").split(",");
		String lowSalaryCustomerPercent=prop.getProperty("FraudAlert_LowSalary_Customer_Percent");
		String mediumSalaryCustomerPercent=prop.getProperty("FraudAlert_MediumSalary_Customer_Percent");
		String highSalaryCustomerPercent=prop.getProperty("FraudAlert_HighSalary_Customer_Percent");
		String deMonetizationCRNoTimeIncr=prop.getProperty("FraudAlert_Demonetization_CR_Incr_No_Time");
		String deMonetizationDRNoTimeIncr=prop.getProperty("FraudAlert_Demonetization_DR_Incr_No_Time");
		String drTxnComment[] =prop.getProperty("FraudAlert_Transaction_DR_Comment").split(",");
		String fraudCRProcess[] =prop.getProperty("FraudAlert_Fraud_CR_enable_process").split(",");
		String fraudCRTxnNoIncr[] =prop.getProperty("FraudAlert_Fraud_Sudden_CR_incr_no").split(",");
		
		List<String>  fraudCRProcessList = new ArrayList<String>(fraudCRProcess.length);
		for (int i = 0; i < fraudCRProcess.length; i++) {
			fraudCRProcessList.add(fraudCRProcess[i]);
		}
		
		List<Integer>  fraudCRTxnNoIncrList = new ArrayList<Integer>(fraudCRTxnNoIncr.length);
		for (int i = 0; i < fraudCRTxnNoIncr.length; i++) {
			fraudCRTxnNoIncrList.add(Integer.valueOf(fraudCRTxnNoIncr[i]));
		}
		
		
		List<String>  drTxnCommentList = new ArrayList<String>(drTxnComment.length);
		for (int i = 0; i < drTxnComment.length; i++) {
			drTxnCommentList.add(drTxnComment[i]);
		}
		List<String>  txnTypeList = new ArrayList<String>(txnTypesArr.length);
		for (int i = 0; i < txnTypesArr.length; i++) {
			txnTypeList.add(txnTypesArr[i]);
			
		}
		String month[] = months.split(",");
		List<Integer> monthsArrList  = new ArrayList<Integer>(month.length);
		for (int i = 0; i < month.length; i++) {
			monthsArrList.add(Integer.valueOf(month[i]));
		}
		String year[] = years.split(",");
		List<Integer> yearsArrList  = new ArrayList<Integer>(years.length());
		for (int i = 0; i < year.length; i++) {
			yearsArrList.add(Integer.valueOf(year[i]));
		}
		
		
		configObj.setMasterEmployeeFilePath(masterEmployeeFilePath);
		configObj.setCustomerTxnDataFilePath(customerTxnDataFilePath);
		configObj.setDelimiter(delimiter);
		configObj.setDataSize(dataSize);
		configObj.setSampleDataStartDate(sampleDataStartDate);
		configObj.setSampleDataEndDate(sampleDataEndDate);
		configObj.setDemonetizationThresoldDate(demonetizationDate);
		configObj.setMaxLowSalaryRange(lowSalary);
		configObj.setMaxMediumSalaryRange(mediumSalary);
		configObj.setMaxHighSalaryRange(highSalary);
		configObj.setMonths(monthsArrList);
		configObj.setYears(yearsArrList);
		configObj.setLowSalaryTxnNoDRAndCRBefore(getDRAndCRTxnList(lowSalaryTxnNoDRAndCRBefore));
		configObj.setMediumSalaryTxnNoDRAndCRBefore(getDRAndCRTxnList(mediumSalaryTxnNoDRAndCRBefore));
		configObj.setHighSalaryTxnNoDRAndCRBefore(getDRAndCRTxnList(highSalaryTxnNoDRAndCRBefore));
		
		configObj.setLowSalaryTxnNoDRAndCRAfter(getDRAndCRTxnList(lowSalaryDRTxnNoAndCRAfter));
		configObj.setMediumSalaryTxnNoDRAndCRAfter(getDRAndCRTxnList(mediumSalaryTxnNoDRAndCRAfter));
		configObj.setHighSalaryTxnNoDRAndCRAfter(getDRAndCRTxnList(highSalaryTxnNoDRAndCRAfter));
		
		configObj.setLowSalaryCRBefore(getDRAndCRAmtList(lowSalaryCR));
		configObj.setMediumSalaryCRBefore(getDRAndCRAmtList(mediumSalaryCR));
		configObj.setHighSalaryCRBefore(getDRAndCRAmtList(highSalaryCR));
		configObj.setLowSalaryDRBefore(getDRAndCRAmtList(lowSalaryDR));
		configObj.setMediumSalaryDRBefore(getDRAndCRAmtList(mediumSalaryDR));
		configObj.setHighSalaryDRBefore(getDRAndCRAmtList(highSalaryDR));
		
		configObj.setLowSalaryDRAfter(getDRAndCRAmtList(lowSalaryDRAfter));
		configObj.setMediumSalaryDRAfter(getDRAndCRAmtList(mediumSalaryDRAfter));
		configObj.setHighSalaryDRAfter(getDRAndCRAmtList(highSalaryDRAfter));
		
		configObj.setDemonitizationCR(getDRAndCRAmtList(deMonetizationCR));
		
		configObj.setLowSalaryCustomerPercent(Float.valueOf(lowSalaryCustomerPercent));
		configObj.setMediumSalaryCustomerPercent(Float.valueOf(mediumSalaryCustomerPercent));
		configObj.setHighSalaryCustomerPercent(Float.valueOf(highSalaryCustomerPercent));
		
		configObj.setDeMonetizationCRNoTimeIncr(Integer.valueOf(deMonetizationCRNoTimeIncr));
		configObj.setDeMonetizationDRNoTimeIncr(Integer.valueOf(deMonetizationDRNoTimeIncr));
		configObj.setDrTxnSummaryList(drTxnCommentList);
		configObj.setTxnTypeList(txnTypeList);
		configObj.setFraudCRProcess(fraudCRProcessList);
		configObj.setFraudCRTxnNoIncr(fraudCRTxnNoIncrList);
		return configObj;
	}
	
	private List<Integer> getDRAndCRTxnList(String txnDRAndCR) {
		List<Integer> salaryDrAndCrList = new ArrayList<Integer>();
		String txnDRAndCRStrArr [] = txnDRAndCR.split(",");
		for (int count = 0; count < txnDRAndCRStrArr.length; count++) {
			Integer value = Integer.valueOf(txnDRAndCRStrArr[count].trim());
			salaryDrAndCrList.add(value);
		}
		return salaryDrAndCrList;
	}
	
	private List<Double> getDRAndCRAmtList(String [] txnAmtDRAndCR) {
		List<Double> txnAmtDrAndCrList = new ArrayList<Double>();
		for (int count = 0; count < txnAmtDRAndCR.length; count++) {
			Double value = Double.valueOf(txnAmtDRAndCR[count].trim());
			txnAmtDrAndCrList.add(value);
		}
		return txnAmtDrAndCrList;
	}
}
