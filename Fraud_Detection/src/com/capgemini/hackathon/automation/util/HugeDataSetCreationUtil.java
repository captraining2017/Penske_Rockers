package com.capgemini.hackathon.automation.util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import com.capgemini.hackathon.automation.model.ConfigurationModel;
import com.capgemini.hackathon.automation.model.Employee;

public class HugeDataSetCreationUtil {

	private static final String COMMA_DELIMITER = ",";
	private static final String FILE_HEADER = "Cust Id,Txn Date,Txn Amount,DR/CR,Summary";
	private static SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	private static final String NEW_LINE_SEPARATOR = "\n";
    private static Long lowSalaryCRMonthlyCount =0L;
    private static Long mediumSalaryCRMonthlyCount =0L;
    private static Long highSalaryCRMonthlyCount =0L;
	
    private static Long lowSalaryDRMonthlyCount =0L;
    private static Long mediumSalaryDRMonthlyCount =0L;
    private static Long highSalaryDRMonthlyCount =0L;
    private static boolean demonitizationFlagEnabled=false;
	
	public static void generateSampleTxnData( ConfigurationModel configObj) {
		
		FileWriter fileWriter = null;
		try {
			List<Employee> employeeList = getEmployeeModelList(configObj.getMasterEmployeeFilePath());
			fileWriter = new FileWriter(configObj.getCustomerTxnDataFilePath());
			fileWriter.append(FILE_HEADER.toString());
			fileWriter.append(NEW_LINE_SEPARATOR);
			Random randomizer = new Random();
			Double dRAndCrDifferent=0.0;
			
			for (int yearCount = 0; yearCount < configObj.getYears().size(); yearCount++) {
				for (int monthCount = 0; monthCount < configObj.getMonths().size(); monthCount++) {
					int year=configObj.getYears().get(yearCount);
					int month=configObj.getMonths().get(monthCount);
					boolean isMonthlyTxnCompleted=false;
					lowSalaryCRMonthlyCount =0L;
				    mediumSalaryCRMonthlyCount =0L;
				    highSalaryCRMonthlyCount =0L;
					
				    lowSalaryDRMonthlyCount =0L;
				    mediumSalaryDRMonthlyCount =0L;
				    highSalaryDRMonthlyCount =0L;
					
					Date firstDateOfMonth = DateUtil.getFirstMonthOfDate(year,month);
					Date fraudAlertEndDate = DateUtil.getFormatedDate("dd/MM/yyyy", configObj.getSampleDataEndDate());
					if(firstDateOfMonth.compareTo(fraudAlertEndDate) <0)
					{
						for (Employee employee : employeeList) {
							// monthly starting salary credit transaction
							Double monthlySalary= employee.getSalary() ; 
							employee.setNoOfBankCR(0);
							employee.setNoOfBankDR(0);
							employee.setCreditAmt(monthlySalary);
							employee.setDebitAmt(0.0);
							employee.setSummary("SALARY");
							writeTxnFile(fileWriter, firstDateOfMonth, employee, monthlySalary, configObj.getDelimiter(),"CR" );
							employee.setNoOfBankCR(employee.getNoOfBankCR() +1);
							System.out.println(employee.getEmployeeId()+","+firstDateOfMonth.toString()+","+monthlySalary+","+"CR" +"," +employee.getNoOfBankDR()+","+employee.getNoOfBankCR()+","+lowSalaryCRMonthlyCount);
							
						}
						lowSalaryCRMonthlyCount = (configObj.getLowSalaryCustomerPercent().longValue() ) * employeeList.size() / 100;
						mediumSalaryCRMonthlyCount= (configObj.getMediumSalaryCustomerPercent().longValue() )  * employeeList.size()/100 ;
						highSalaryCRMonthlyCount =(configObj.getHighSalaryCustomerPercent().longValue() )  * employeeList.size() / 100 ;
						while (!isMonthlyTxnCompleted) {
							// Other Debit and Credit transaction 
							List<Date> eachMonthDates = DateUtil.getMonthDateList(month, firstDateOfMonth);
							Date randomDate=eachMonthDates.get(randomizer.nextInt(eachMonthDates.size()));
							Employee randomEmployee=employeeList.get(randomizer.nextInt(employeeList.size()));
							String txnType =configObj.getTxnTypeList().get(randomizer.nextInt(configObj.getTxnTypeList().size()));
							String drTxnComment =configObj.getDrTxnSummaryList().get(randomizer.nextInt(configObj.getDrTxnSummaryList().size()));
							Double txnAmt=0.0;
							if ("LOAN".equalsIgnoreCase(drTxnComment.trim()) ) {
								if(randomEmployee.getLoanAmount() ==0.0) {
									txnAmt = getCustomerTxnAmount(randomEmployee, configObj, txnType, randomizer);
									randomEmployee.setLoanAmount(txnAmt);
								}
								txnAmt = randomEmployee.getLoanAmount() ;
							}
							else {
								txnAmt = getCustomerTxnAmount(randomEmployee, configObj, txnType, randomizer);
							}
							
							isMonthlyTxnCompleted = true;
							if(randomEmployee.getSalary()<= configObj.getMaxLowSalaryRange() && !isMonthlyTxnLowSalaryCompleted(configObj,txnType,randomDate)) {
								isMonthlyTxnCompleted = false;
								if("CR".equalsIgnoreCase(txnType)) {
									if(!demonitizationFlagEnabled && configObj.getLowSalaryTxnNoDRAndCRBefore().get(1)>randomEmployee.getNoOfBankCR()) {
										randomEmployee.setNoOfBankCR(randomEmployee.getNoOfBankCR() +1);
										randomEmployee.setCreditAmt(randomEmployee.getCreditAmt() + txnAmt);
										randomEmployee.setSummary("OTHERS");
										writeTxnFile(fileWriter, randomDate, randomEmployee, txnAmt,configObj.getDelimiter(),txnType );
									}
									if(demonitizationFlagEnabled ) {
										Integer predictedDemonCRTxnNo=configObj.getLowSalaryTxnNoDRAndCRAfter().get(1) * configObj.getDeMonetizationCRNoTimeIncr();
										if(predictedDemonCRTxnNo>randomEmployee.getNoOfBankCR()) {
											randomEmployee.setSummary("OTHERS");
											randomEmployee.setNoOfBankCR(randomEmployee.getNoOfBankCR() +1);
											randomEmployee.setCreditAmt(randomEmployee.getCreditAmt() + txnAmt);
											writeTxnFile(fileWriter, randomDate, randomEmployee, txnAmt,configObj.getDelimiter(),txnType );
											String isfraudCRProcesEnable=configObj.getFraudCRProcess().get(randomizer.nextInt(configObj.getFraudCRProcess().size()));
											if("true".equalsIgnoreCase(isfraudCRProcesEnable)) {
												fraudCRAfterDemonetization(randomizer, month, firstDateOfMonth, employeeList, configObj, fileWriter);
											}
										}
									}
									++lowSalaryCRMonthlyCount;
								}
								else if("DR".equalsIgnoreCase(txnType)  ) {
									dRAndCrDifferent=randomEmployee.getCreditAmt() - randomEmployee.getDebitAmt();
									if(!demonitizationFlagEnabled && configObj.getLowSalaryTxnNoDRAndCRAfter().get(0) >randomEmployee.getNoOfBankDR()
											&& dRAndCrDifferent>=0.0) {
											randomEmployee.setNoOfBankDR(randomEmployee.getNoOfBankDR() +1);
											randomEmployee.setDebitAmt(randomEmployee.getDebitAmt() + txnAmt);
											randomEmployee.setSummary(drTxnComment);
											writeTxnFile(fileWriter, randomDate, randomEmployee, txnAmt,configObj.getDelimiter(),txnType );
											//System.out.println(randomEmployee.getEmployeeId()+","+randomDate+","+randomEmployee.getCreditAmt()+","+randomEmployee.getDebitAmt()+","+dRAndCrDifferent);
									}
									if(demonitizationFlagEnabled ) {
										Integer predictedDemonDRTxnNo=configObj.getLowSalaryTxnNoDRAndCRAfter().get(0) *   configObj.getDeMonetizationDRNoTimeIncr();
										randomEmployee.setDebitAmt(randomEmployee.getDebitAmt() + txnAmt);
										if(predictedDemonDRTxnNo>randomEmployee.getNoOfBankDR()   ){
											randomEmployee.setNoOfBankDR(randomEmployee.getNoOfBankDR() +1);
											randomEmployee.setSummary(drTxnComment);
											if(dRAndCrDifferent>=0.0)
												writeTxnFile(fileWriter, randomDate, randomEmployee, txnAmt,configObj.getDelimiter(),txnType );
											//System.out.println(randomEmployee.getEmployeeId()+","+randomDate+","+randomEmployee.getCreditAmt()+","+randomEmployee.getDebitAmt()+","+dRAndCrDifferent);
									}
								}
									++lowSalaryDRMonthlyCount;
							}
								
						}
							if(configObj.getMaxLowSalaryRange() < randomEmployee.getSalary() &&  randomEmployee.getSalary()<= configObj.getMaxMediumSalaryRange()
									&& !isMonthlyTxnMediumSalaryCompleted(configObj, txnType,randomDate)) {
								isMonthlyTxnCompleted = false;
								if("CR".equalsIgnoreCase(txnType)  ) {
									if(!demonitizationFlagEnabled && configObj.getMediumSalaryTxnNoDRAndCRBefore().get(1)>randomEmployee.getNoOfBankCR()) {
										randomEmployee.setNoOfBankCR(randomEmployee.getNoOfBankCR() +1);
										randomEmployee.setCreditAmt(randomEmployee.getCreditAmt() + txnAmt);
										randomEmployee.setSummary("OTHERS");
										writeTxnFile(fileWriter, randomDate, randomEmployee, txnAmt,configObj.getDelimiter(),txnType );
									}
									if(demonitizationFlagEnabled ) {
										Integer predictedDemonCRTxnNo=configObj.getMediumSalaryTxnNoDRAndCRAfter().get(1) * configObj.getDeMonetizationCRNoTimeIncr();
										if(predictedDemonCRTxnNo>randomEmployee.getNoOfBankCR()) {
											
											randomEmployee.setNoOfBankCR(randomEmployee.getNoOfBankCR() +1);
											randomEmployee.setCreditAmt(randomEmployee.getCreditAmt() + txnAmt);
											randomEmployee.setSummary("OTHERS");
											writeTxnFile(fileWriter, randomDate, randomEmployee, txnAmt,configObj.getDelimiter(),txnType );
											String isfraudCRProcesEnable=configObj.getFraudCRProcess().get(randomizer.nextInt(configObj.getFraudCRProcess().size()));
											if("true".equalsIgnoreCase(isfraudCRProcesEnable)) {
												fraudCRAfterDemonetization(randomizer, month, firstDateOfMonth, employeeList, configObj, fileWriter);
											}
										}
									}
									++mediumSalaryCRMonthlyCount;
								}
								
								else if("DR".equalsIgnoreCase(txnType)  ) {
									dRAndCrDifferent=randomEmployee.getCreditAmt() - randomEmployee.getDebitAmt();
									if(!demonitizationFlagEnabled && configObj.getMediumSalaryTxnNoDRAndCRBefore().get(0)>randomEmployee.getNoOfBankDR()
											&&  dRAndCrDifferent>=0.0) {
										
										randomEmployee.setNoOfBankDR(randomEmployee.getNoOfBankDR() +1);
										randomEmployee.setDebitAmt(randomEmployee.getDebitAmt() + txnAmt);
										randomEmployee.setSummary(drTxnComment);
										if(dRAndCrDifferent>=0.0)
											writeTxnFile(fileWriter, randomDate, randomEmployee, txnAmt,configObj.getDelimiter(),txnType);
										//System.out.println(randomEmployee.getEmployeeId()+","+randomDate+","+randomEmployee.getCreditAmt()+","+randomEmployee.getDebitAmt()+","+dRAndCrDifferent);
									}
									if(demonitizationFlagEnabled  ) {
										Integer predictedDemonDRTxnNo=configObj.getMediumSalaryTxnNoDRAndCRAfter().get(0) * configObj.getDeMonetizationDRNoTimeIncr();
										randomEmployee.setDebitAmt(randomEmployee.getDebitAmt() + txnAmt);
										if(predictedDemonDRTxnNo>randomEmployee.getNoOfBankDR() &&  dRAndCrDifferent>=0.0){
											randomEmployee.setNoOfBankDR(randomEmployee.getNoOfBankDR() +1);
											randomEmployee.setSummary(drTxnComment);
											if(dRAndCrDifferent>=0.0)
												writeTxnFile(fileWriter, randomDate, randomEmployee, txnAmt,configObj.getDelimiter(),txnType );
											//System.out.println(randomEmployee.getEmployeeId()+","+randomDate+","+randomEmployee.getCreditAmt()+","+randomEmployee.getDebitAmt()+","+dRAndCrDifferent);
										}
									}
									++mediumSalaryDRMonthlyCount;
								}
								
							}
							if (configObj.getMaxMediumSalaryRange() < randomEmployee.getSalary() && randomEmployee.getSalary()<= configObj.getMaxHighSalaryRange()
									&& !isMonthlyTxnHighSalaryCompleted(configObj, txnType,randomDate)) {
								isMonthlyTxnCompleted =false; 
								if("CR".equalsIgnoreCase(txnType) ) {
									if(!demonitizationFlagEnabled && configObj.getMediumSalaryTxnNoDRAndCRBefore().get(1)>randomEmployee.getNoOfBankCR()) {
										randomEmployee.setNoOfBankCR(randomEmployee.getNoOfBankCR() +1);
										randomEmployee.setCreditAmt(randomEmployee.getCreditAmt() + txnAmt);
										randomEmployee.setSummary("OTHERS");
										writeTxnFile(fileWriter, randomDate, randomEmployee, txnAmt,configObj.getDelimiter(),txnType );
									}
									if(demonitizationFlagEnabled  ) {
										Integer predictedDemonCRTxnNo=configObj.getHighSalaryTxnNoDRAndCRAfter().get(1) * configObj.getDeMonetizationCRNoTimeIncr();
										if(predictedDemonCRTxnNo>=randomEmployee.getNoOfBankCR())
										{
											randomEmployee.setNoOfBankCR(randomEmployee.getNoOfBankCR() +1);
											randomEmployee.setCreditAmt(randomEmployee.getCreditAmt() + txnAmt);
											randomEmployee.setSummary("OTHERS");
											writeTxnFile(fileWriter, randomDate, randomEmployee, txnAmt,configObj.getDelimiter(),txnType );
											String isfraudCRProcesEnable=configObj.getFraudCRProcess().get(randomizer.nextInt(configObj.getFraudCRProcess().size()));
											if("true".equalsIgnoreCase(isfraudCRProcesEnable)) {
												fraudCRAfterDemonetization(randomizer, month, firstDateOfMonth, employeeList, configObj, fileWriter);
											}
										}
									}
									++highSalaryCRMonthlyCount;
								}
								else if("DR".equalsIgnoreCase(txnType)  ) {
									dRAndCrDifferent=randomEmployee.getCreditAmt() - randomEmployee.getDebitAmt();
									if(!demonitizationFlagEnabled && configObj.getHighSalaryTxnNoDRAndCRBefore().get(0)>randomEmployee.getNoOfBankDR()
											&&  dRAndCrDifferent>=0.0) {
										randomEmployee.setNoOfBankDR(randomEmployee.getNoOfBankDR() +1);
										randomEmployee.setDebitAmt(randomEmployee.getDebitAmt() + txnAmt);
										randomEmployee.setSummary(drTxnComment);
										if(dRAndCrDifferent>=0.0)
											writeTxnFile(fileWriter, randomDate, randomEmployee, txnAmt,configObj.getDelimiter(),txnType);
										//System.out.println(randomEmployee.getEmployeeId()+","+randomDate+","+randomEmployee.getCreditAmt()+","+randomEmployee.getDebitAmt()+","+dRAndCrDifferent);
									}
									if(demonitizationFlagEnabled ) {
										Integer predictedDemonDRTxnNo=configObj.getHighSalaryTxnNoDRAndCRAfter().get(0) * configObj.getDeMonetizationDRNoTimeIncr();
										randomEmployee.setDebitAmt(randomEmployee.getDebitAmt() + txnAmt);
										if(predictedDemonDRTxnNo>randomEmployee.getNoOfBankDR() &&  dRAndCrDifferent>=0.0) {
											randomEmployee.setNoOfBankDR(randomEmployee.getNoOfBankDR() +1);
											randomEmployee.setSummary(drTxnComment);
											if(dRAndCrDifferent>=0.0)
												writeTxnFile(fileWriter, randomDate, randomEmployee, txnAmt,configObj.getDelimiter(),txnType );
											//System.out.println(randomEmployee.getEmployeeId()+","+randomDate+","+randomEmployee.getCreditAmt()+","+randomEmployee.getDebitAmt()+","+dRAndCrDifferent);
										}
									}
									++highSalaryDRMonthlyCount;
								}
								
							}
						}
					}
					else{
						break;
					}
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 finally 
		 {
			 if(fileWriter != null) {
				 try {
					fileWriter.flush();
					fileWriter.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				 
		      }
		 }
		        
		
		
		
	}
	
	private static boolean isMonthlyTxnLowSalaryCompleted(ConfigurationModel configObj,String txnType, Date txnDate) {
		
		Float maxMonthCrLowSalaryTxNo=0.0F;
		Float maxMonthDrLowSalaryTxNo=0.0F;
		Date formattedTxnDate=null;
		Date formattedDemonetizationDate=null;
		 try {
			formattedTxnDate=DateUtil.getFormatedDate("dd/MM/yyyy", txnDate);
			formattedDemonetizationDate=DateUtil.getFormatedDate("dd/MM/yyyy", configObj.getDemonetizationThresoldDate());
			if (formattedTxnDate.compareTo(formattedDemonetizationDate) < 0 && !demonitizationFlagEnabled) {
				maxMonthCrLowSalaryTxNo =configObj.getLowSalaryCustomerPercent() * configObj.getLowSalaryTxnNoDRAndCRBefore().get(1);
				maxMonthDrLowSalaryTxNo =configObj.getLowSalaryCustomerPercent() * configObj.getLowSalaryTxnNoDRAndCRBefore().get(0);
			}
			else {
				demonitizationFlagEnabled=true;
				maxMonthCrLowSalaryTxNo =configObj.getLowSalaryCustomerPercent() * configObj.getDeMonetizationCRNoTimeIncr()* configObj.getLowSalaryTxnNoDRAndCRAfter().get(1);
				maxMonthDrLowSalaryTxNo =configObj.getLowSalaryCustomerPercent() * configObj.getDeMonetizationDRNoTimeIncr()* configObj.getLowSalaryTxnNoDRAndCRAfter().get(0);
			}
			if( (maxMonthCrLowSalaryTxNo>=lowSalaryCRMonthlyCount) || (maxMonthDrLowSalaryTxNo>=lowSalaryDRMonthlyCount)) 
			{
				
				return false;
			}
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			return true;

	}
	
private static boolean isMonthlyTxnMediumSalaryCompleted(ConfigurationModel configObj,String txnType, Date txnDate) {
	Float maxMonthCrMediumSalaryTxNo=0.0F;
	Float maxMonthDrMediumSalaryTxNo=0.0F;
	Date formattedTxnDate=null;
	Date formattedDemonetizationDate=null;
	 try {
		formattedTxnDate=DateUtil.getFormatedDate("dd/MM/yyyy", txnDate);
		formattedDemonetizationDate=DateUtil.getFormatedDate("dd/MM/yyyy", configObj.getDemonetizationThresoldDate());
		if (formattedTxnDate.compareTo(formattedDemonetizationDate) < 0) {
			maxMonthCrMediumSalaryTxNo =configObj.getMediumSalaryCustomerPercent() * configObj.getMediumSalaryTxnNoDRAndCRBefore().get(1);
			 maxMonthDrMediumSalaryTxNo =configObj.getMediumSalaryCustomerPercent() * configObj.getMediumSalaryTxnNoDRAndCRBefore().get(0);
		}
		else {
			demonitizationFlagEnabled=true;
			maxMonthCrMediumSalaryTxNo =configObj.getMediumSalaryCustomerPercent()* configObj.getDeMonetizationCRNoTimeIncr() * configObj.getMediumSalaryTxnNoDRAndCRAfter().get(1);
			 maxMonthDrMediumSalaryTxNo =configObj.getMediumSalaryCustomerPercent()* configObj.getDeMonetizationDRNoTimeIncr() * configObj.getMediumSalaryTxnNoDRAndCRAfter().get(0);
		}
		if(  (maxMonthCrMediumSalaryTxNo>=mediumSalaryCRMonthlyCount) || (maxMonthDrMediumSalaryTxNo>=mediumSalaryDRMonthlyCount)) 
		{
			return false;
		}
		
	} catch (ParseException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
		return true;
	}

private static boolean isMonthlyTxnHighSalaryCompleted(ConfigurationModel configObj,String txnType, Date txnDate) {
	Long maxMonthCrHighSalaryTxNo=0L;
	Long maxMonthDrHighSalaryTxNo=0L;
	Date formattedTxnDate=null;
	Date formattedDemonetizationDate=null;
	 try {
		formattedTxnDate=DateUtil.getFormatedDate("dd/MM/yyyy", txnDate);
		formattedDemonetizationDate=DateUtil.getFormatedDate("dd/MM/yyyy", configObj.getDemonetizationThresoldDate());
		if (formattedTxnDate.compareTo(formattedDemonetizationDate) < 0) {
			maxMonthCrHighSalaryTxNo =configObj.getHighSalaryCustomerPercent().longValue() * configObj.getHighSalaryTxnNoDRAndCRBefore().get(1);
			maxMonthDrHighSalaryTxNo =configObj.getHighSalaryCustomerPercent().longValue() * configObj.getHighSalaryTxnNoDRAndCRBefore().get(0);
		}
		else {
			demonitizationFlagEnabled=true;
			maxMonthCrHighSalaryTxNo =configObj.getHighSalaryCustomerPercent().longValue() * configObj.getDeMonetizationCRNoTimeIncr() * configObj.getHighSalaryTxnNoDRAndCRAfter().get(1);
			maxMonthDrHighSalaryTxNo =configObj.getHighSalaryCustomerPercent().longValue() * configObj.getDeMonetizationDRNoTimeIncr() * configObj.getHighSalaryTxnNoDRAndCRAfter().get(0);
		}
		if  ((maxMonthCrHighSalaryTxNo>=highSalaryCRMonthlyCount) || (maxMonthDrHighSalaryTxNo>=highSalaryDRMonthlyCount)) 
		{
			return false;
		}
		
	} catch (ParseException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
		return true;
	}

	
	private static Double getCustomerTxnAmount(Employee randomEmployee,ConfigurationModel configObj, String txnType,Random randomizer) {
		Double txnAmt=null;
		if(!demonitizationFlagEnabled) {
			if("CR".equalsIgnoreCase(txnType)   ) 
			{
				if(randomEmployee.getSalary()<= configObj.getMaxLowSalaryRange()) {
					txnAmt = configObj.getLowSalaryCRBefore().get(randomizer.nextInt( configObj.getLowSalaryCRBefore().size()));
				}
				else if(randomEmployee.getSalary()<= configObj.getMaxMediumSalaryRange() ) {
					txnAmt = configObj.getMediumSalaryCRBefore().get(randomizer.nextInt( configObj.getMediumSalaryCRBefore().size()));
				}
				else if( randomEmployee.getSalary()<= configObj.getMaxHighSalaryRange() ) {
					txnAmt = configObj.getHighSalaryCRBefore().get(randomizer.nextInt( configObj.getHighSalaryCRBefore().size()));
				}
			}
			if("DR".equalsIgnoreCase(txnType)   ) 
			{
				if(randomEmployee.getSalary()<= configObj.getMaxLowSalaryRange()) {
					txnAmt = configObj.getLowSalaryDRBefore().get(randomizer.nextInt( configObj.getLowSalaryDRBefore().size()));
				}
				else if(randomEmployee.getSalary()<= configObj.getMaxMediumSalaryRange() ) {
					txnAmt = configObj.getMediumSalaryDRBefore().get(randomizer.nextInt( configObj.getMediumSalaryDRBefore().size()));
				}
				else if( randomEmployee.getSalary()<= configObj.getMaxHighSalaryRange() ) {
					txnAmt = configObj.getHighSalaryDRBefore().get(randomizer.nextInt( configObj.getHighSalaryDRBefore().size()));
				}
			}
		}
		else {
			if("CR".equalsIgnoreCase(txnType)   ) 
			{
				if(randomEmployee.getSalary()<= configObj.getMaxLowSalaryRange()) {
					txnAmt = configObj.getDemonitizationCR().get(randomizer.nextInt( configObj.getDemonitizationCR().size()));
				}
				else if(randomEmployee.getSalary()<= configObj.getMaxMediumSalaryRange() ) {
					txnAmt = configObj.getDemonitizationCR().get(randomizer.nextInt( configObj.getDemonitizationCR().size()));
				}
				else if( randomEmployee.getSalary()<= configObj.getMaxHighSalaryRange() ) {
					txnAmt = configObj.getDemonitizationCR().get(randomizer.nextInt( configObj.getDemonitizationCR().size()));
				}
			}
			if("DR".equalsIgnoreCase(txnType)   ) 
			{
				if(randomEmployee.getSalary()<= configObj.getMaxLowSalaryRange()) {
					txnAmt = configObj.getLowSalaryDRAfter().get(randomizer.nextInt( configObj.getLowSalaryDRAfter().size()));
				}
				else if(randomEmployee.getSalary()<= configObj.getMaxMediumSalaryRange() ) {
					txnAmt = configObj.getMediumSalaryDRAfter().get(randomizer.nextInt( configObj.getMediumSalaryDRAfter().size()));
				}
				else if( randomEmployee.getSalary()<= configObj.getMaxHighSalaryRange() ) {
					txnAmt = configObj.getHighSalaryDRAfter().get(randomizer.nextInt( configObj.getHighSalaryDRAfter().size()));
				}
			}
		}

		return txnAmt;
	}
	
	private static List<Employee> getEmployeeModelList(String masterEmpFileName) throws IOException{
		List<Employee> employeeList = new ArrayList<Employee>();
		FileReader fileReader = null;
		BufferedReader bufReader = null;
		try 
		{
			String line = "";
			fileReader = new FileReader(masterEmpFileName);
			bufReader = new BufferedReader(fileReader);
			//Read the CSV file header to skip it
			bufReader.readLine();
			while ((line = bufReader.readLine()) != null) {
				                //Get all tokens available in line
		         String[] tokens = line.split(COMMA_DELIMITER);
		         if (tokens != null & tokens.length >0) {
						Employee employee = new Employee();
						employee.setEmployeeId(Integer.valueOf(tokens[0]));
						employee.setEmployeeName((tokens[1]));
						employee.setSalary(Double.valueOf(tokens[2]));
						employeeList.add(employee);
				}
			}

		} 
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		}
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		finally {
			if (bufReader != null ) {
				bufReader.close();
			}
			if (fileReader != null) {
				fileReader.close();
			}
		}

		return employeeList;
	}
	
	private static void fraudCRAfterDemonetization(Random randomizer,int month, Date firstDateOfMonth, List<Employee> employeeList, 
			ConfigurationModel configObj,FileWriter fileWriter) {
		
		List<Date> eachMonthDates = DateUtil.getMonthDateList(month, firstDateOfMonth);
		Date randomDate=eachMonthDates.get(randomizer.nextInt(eachMonthDates.size()));
		Integer crRandomTxnCount=configObj.getFraudCRTxnNoIncr().get(randomizer.nextInt(configObj.getFraudCRTxnNoIncr().size()));
		String txnType ="CR";
		String drTxnComment ="OTHER";
		Double txnAmt=0.0;
		for (int i = 0; i < crRandomTxnCount; i++) {
			Employee randomEmployee=employeeList.get(randomizer.nextInt(employeeList.size()));			
			txnAmt = getCustomerTxnAmount(randomEmployee, configObj, txnType, randomizer);
			writeTxnFile(fileWriter, randomDate, randomEmployee, txnAmt, configObj.getDelimiter(), txnType);
		}
		
	}
	
	private static void writeTxnFile(FileWriter fileWriter, Date txnDate, Employee employee, Double txnAmount,String delimiter, 
			String txnType) {
		
		try {
			
			
			String formatedDate = sdf1.format(txnDate);
			fileWriter.append(String.valueOf(employee.getEmployeeId()));
			fileWriter.append(delimiter);
			fileWriter.append(String.valueOf(formatedDate));
			fileWriter.append(delimiter);
			fileWriter.append(String.valueOf(txnAmount));
			fileWriter.append(delimiter);
			fileWriter.append(String.valueOf(txnType));
			fileWriter.append(delimiter);
			fileWriter.append(String.valueOf(employee.getSummary()));
			fileWriter.append(NEW_LINE_SEPARATOR);
			
            //Add a new line separator after the header
             

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
