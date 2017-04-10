package com.capgemini.hackathon.automation.util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.capgemini.hackathon.automation.model.ConfigurationModel;
import com.capgemini.hackathon.automation.model.CustomerTxnModel;

public class FraudReportGeneration {
	
private static boolean isDemonetization = false; 

public static void generateCustomerDemonetizationReport(ConfigurationModel configObj) {
	 List<CustomerTxnModel> customerTxnModelList =readTxnFile(configObj);
	 validateDemonetizationRules(customerTxnModelList,configObj);
}

private static void validateDemonetizationRules( List<CustomerTxnModel> customerTxnModelList, ConfigurationModel configObj) {
	
	for (CustomerTxnModel customerTxnModel : customerTxnModelList) {
		boolean isFraud = false;
		isFraud=isFraudRule1(customerTxnModel, configObj);
	}
	
}

private static boolean isFraudRule1(CustomerTxnModel customerTxnModel, ConfigurationModel configObj) {
	 Iterator<Entry<String,HashMap<String, Double>>> monthlyTxnitr = customerTxnModel.getBeforeDeMoneMonthlyCRAndDRTxnAmt().entrySet().iterator();
	 double [] txnAmountBeforeDemonz=	 getTxnAmount(monthlyTxnitr);
	double avgCrAmtBefore= txnAmountBeforeDemonz[0] / customerTxnModel.getBeforeDeMoneMonthlyCRAndDRTxnAmt().size();
	double avgDrAmtBefore=txnAmountBeforeDemonz[1] / customerTxnModel.getBeforeDeMoneMonthlyCRAndDRTxnAmt().size();
	 monthlyTxnitr = customerTxnModel.getAftereDeMoneMonthlyCRAndDRTxnAmt().entrySet().iterator();
	 double [] txnAmountAfterDemonz=	 getTxnAmount(monthlyTxnitr);
	 double avgCrAmtAfter= txnAmountAfterDemonz[0] / customerTxnModel.getAftereDeMoneMonthlyCRAndDRTxnAmt().size();
	 double avgDrAmtAfter=txnAmountAfterDemonz[1] / customerTxnModel.getAftereDeMoneMonthlyCRAndDRTxnAmt().size();
	 double avgCrThresholdAmt = avgCrAmtBefore + avgDrAmtBefore + avgDrAmtAfter +(configObj.getLowSalaryAvgSavingPercent() / 100) * avgDrAmtBefore;
	 if(avgCrAmtAfter > avgCrThresholdAmt)
		 return true;
	 
	return false;
}

private static double[] getTxnAmount(Iterator<Entry<String,HashMap<String, Double>>> monthlyTxnitr) {
	Double creditAmt = 0.0;
	Double debitAmt = 0.0;
	double txnAmounts[] = {0.0,0.0};
	 while (monthlyTxnitr.hasNext()) {
	        Map.Entry<String,HashMap<String, Double>> pair = (Map.Entry)monthlyTxnitr.next();
	        Iterator<Entry<String, Double>> txnTypeItr= pair.getValue().entrySet().iterator();
	        while(txnTypeItr.hasNext()) {
	        	String txnType = txnTypeItr.next().getKey();
	        	Double txnAmt = txnTypeItr.next().getValue();
	        	if("CR".equals(txnType)) {
	        		creditAmt = creditAmt + txnAmt;
	        	}
	        	else {
	        		debitAmt=debitAmt+ txnAmt;
	        	}
	        }
	 }
	 txnAmounts[0] =creditAmt;
	 txnAmounts[1] =debitAmt;
	 return txnAmounts;
}

	private static List<CustomerTxnModel> readTxnFile(ConfigurationModel configObj){
		BufferedReader br = null;
		FileReader fileReader=null;
		String line = "";
		 String[] data=null;
		 SimpleDateFormat sdf = new SimpleDateFormat ("dd/MM/yyyy HH:mm:ss"); //("dd-M-yyyy hh:mm:ss");
		 int currentMonth=0;
		 int currentYear=0;
		 List<CustomerTxnModel> customersTxnModelList=new ArrayList<>();
		CustomerTxnModel customerTxnModel =null;
		 try {
			fileReader = new FileReader(configObj.getCustomerTxnDataFilePath()); 
			br = new BufferedReader(fileReader);
			
			
			 while ((line = br.readLine()) != null) {
				
				 HashMap<String, HashMap<String, Double>> monthlyCRAndDRTxnAmt = new HashMap<>();
				 data=line.split(configObj.getDelimiter());
				 Date date = null;
				 String monthYear=null;
					try {
						
						
						if (customersTxnModelList.size() != 0) {
							CustomerTxnModel tempCustomerTxnModel =new CustomerTxnModel();
							customerTxnModel.setCustomerId(Integer.valueOf(data[0]));
							int customerObjIndex= 	customersTxnModelList.indexOf(tempCustomerTxnModel);
							if(customerObjIndex != -1) {
								customerTxnModel=customersTxnModelList.get(customerObjIndex);
							}
							else {
								customerTxnModel = new CustomerTxnModel();
								customerTxnModel.setCustomerId(Integer.valueOf(data[0]));
							}
							
						}
						else {
							customerTxnModel = new CustomerTxnModel();
							customerTxnModel.setCustomerId(Integer.valueOf(data[0]));
						}
						if(customerTxnModel.getSalaryCategory() !=null && "SALARY".equalsIgnoreCase(data[4].trim())) {
							Double salary=Double.valueOf(data[2].trim());
							if(salary<= configObj.getMaxLowSalaryRange()) {
								customerTxnModel.setSalaryCategory("LOW");
							}
							else if(configObj.getMaxLowSalaryRange() < salary &&  salary<= configObj.getMaxMediumSalaryRange()) {
								customerTxnModel.setSalaryCategory("MEDIUM");
							}
							else{
								customerTxnModel.setSalaryCategory("HIGH");
							}
						}
						date = sdf.parse(data[1]);
						int month=DateUtil.getMonth(date);
						int year=DateUtil.getYear(date);
						if (currentMonth != month && currentYear != year ){
							currentMonth=month;
							currentYear=year;
						}
						setDemonetizationEnabled(configObj, date);
						if(currentMonth ==month && currentYear==year) {
							monthYear=DateUtil.getMonthYear(date);
							HashMap<String, Double> txnAmtMap=null;
							if(!isDemonetization) {
								if( customerTxnModel.getBeforeDeMoneMonthlyCRAndDRTxnAmt().get(monthYear) == null) {
									txnAmtMap=new HashMap<>();
									txnAmtMap.put(data[3], Double.valueOf(data[2].trim()));
									monthlyCRAndDRTxnAmt.put(monthYear, txnAmtMap);
									customerTxnModel.setBeforeDeMoneMonthlyCRAndDRTxnAmt(monthlyCRAndDRTxnAmt);
									
								}
								else {
									txnAmtMap=customerTxnModel.getBeforeDeMoneMonthlyCRAndDRTxnAmt().get(monthYear);
									Double txnAmt= txnAmtMap.get(data[3]) + Double.valueOf(data[2].trim());
									txnAmtMap.put(data[3], txnAmt);
									customerTxnModel.getBeforeDeMoneMonthlyCRAndDRTxnAmt().put(monthYear, txnAmtMap);
								}
							}
							else {
								if( customerTxnModel.getAftereDeMoneMonthlyCRAndDRTxnAmt().get(monthYear) == null) {
									txnAmtMap=new HashMap<>();
									txnAmtMap.put(data[3], Double.valueOf(data[2].trim()));
									monthlyCRAndDRTxnAmt.put(monthYear, txnAmtMap);
									customerTxnModel.setAftereDeMoneMonthlyCRAndDRTxnAmt(monthlyCRAndDRTxnAmt);
									
								}
								else {
									txnAmtMap=customerTxnModel.getAftereDeMoneMonthlyCRAndDRTxnAmt().get(monthYear);
									Double txnAmt = Double.valueOf(data[2].trim());
									if("DR".equalsIgnoreCase(data[3]) && !(data[3].startsWith("CASH") || data[3].startsWith("BANK") ) )
									{
										txnAmt=0.0;
									}
									Double txnAmt= txnAmtMap.get(data[3]) + txnAmt;
									txnAmtMap.put(data[3], txnAmt);
									customerTxnModel.getAftereDeMoneMonthlyCRAndDRTxnAmt().put(monthYear, txnAmtMap);
								}
							}
							customersTxnModelList.add(customerTxnModel);
						}
						
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			 }
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 finally {
			 
				try {
					if (fileReader != null)
					fileReader.close();
					 if(br !=null)
						 br.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
		 }
		 return customersTxnModelList;
	}
	
private static void setDemonetizationEnabled(ConfigurationModel configObj, Date txnDate) {
		
		
		Date formattedTxnDate=null;
		Date formattedDemonetizationDate=null;
		 try {
			formattedTxnDate=DateUtil.getFormatedDate("dd/MM/yyyy", txnDate);
			formattedDemonetizationDate=DateUtil.getFormatedDate("dd/MM/yyyy", configObj.getDemonetizationThresoldDate());
			if (formattedTxnDate.compareTo(formattedDemonetizationDate) >= 0 ) {
				isDemonetization = true;
			}
			
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 isDemonetization =  false;

	}
}
