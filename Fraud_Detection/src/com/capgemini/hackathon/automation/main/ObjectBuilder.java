package com.capgemini.hackathon.automation.main;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.capgemini.hackathon.automation.model.CustInfo;
import com.capgemini.hackathon.automation.model.CustomerCategory;
import com.capgemini.hackathon.automation.model.DailyTransaction;
import com.capgemini.hackathon.automation.model.Patterns;
import com.capgemini.hackathon.automation.util.CSVUtils;

public class ObjectBuilder {
	
	String csvFile = "";         
    BufferedReader br = null;
    String line = "";
    String cvsSplitBy = ","; 
    
    Patterns transactionPatterns;
    
    CustomerCategory customerClassification;
    
    Map<Long,CustInfo> customerInfo = new HashMap<Long,CustInfo> ();
	
	public void buildCustomerTransactionModel() {
		
		customerClassification = this.buildCustomerCategory();		
		
		transactionPatterns = this.buildFraudPatterns(customerClassification);
		
		customerInfo = this.buildCustomerClassificationModel(customerClassification);
		
		csvFile = "C:\\projects\\Fraud Detection\\data\\testData.csv";
		
		 Map<Long,Map<String, DailyTransaction>> custMonTransMap = null; 
		 
	        try {

	            br = new BufferedReader(new FileReader(csvFile));
	            if ((line = br.readLine()) != null) {
	            	// String[] headers = line.split(cvsSplitBy);
	            }
	            while ((line = br.readLine()) != null) {
	            	boolean isCredit = true;
	                // use comma as separator
	                String[] data = line.split(cvsSplitBy);   
	                int intMonth = 0;
	                int intDay = 0;
	               
	                SimpleDateFormat sdf = new SimpleDateFormat ("dd/M/yyyy hh:mm:ss"); //("dd-M-yyyy hh:mm:ss");
	                Date date = null;
					try {					
						date = sdf.parse(data[2]);					
					} catch (ParseException e) {						
						e.printStackTrace();
					}
					sdf.applyPattern("dd-M-yyyy");	
					
					String month[] = sdf.format(date).split("-");					 				
					
					if(data.length >= 5 && !data[4].isEmpty()) {
						if (data[4].equalsIgnoreCase("DB")) {
							isCredit = false;
						}
					}
					
					intMonth = Integer.parseInt(month[1]);
					intDay = Integer.parseInt(month[0]);
					
					if (month[2].equalsIgnoreCase("2017")) {
						
						intMonth += 12;
						month[1] = String.valueOf(intMonth);
					}
					
					DailyTransaction monTrans =  new DailyTransaction();
					HashMap<String, DailyTransaction> monthTrans = new HashMap<String, DailyTransaction>();
					if (custMonTransMap != null) {					
						if (custMonTransMap.containsKey(Long.parseLong(data[0])) ) {
							monthTrans = (HashMap<String, DailyTransaction>) custMonTransMap.get(Long.parseLong(data[0]));
							
		                	if (monthTrans.containsKey(month[1])) {
		                		monTrans = monthTrans.get(month[1]);	                		
		                		if (!isCredit) {	                	
		                			monTrans.setDebitCount(monTrans.getDebitCount() + 1);
		                			monTrans.setTotalDebits(monTrans.getTotalDebits() + (Double.parseDouble(data[3])));
	                			}
		                		
		                		if (isCredit) {  	                			
		                			monTrans.setCreditCount(monTrans.getCreditCount() + 1);	                			
		                			monTrans.setTotalCredits(monTrans.getTotalCredits() + (Double.parseDouble(data[3])));
	                			}
		                		
		                	} else {		                		
	                			
	                			if (!isCredit) {                				
	                				monTrans.setDebitCount(1);                 				
	                				monTrans.setTotalDebits((Double.parseDouble(data[3])));                				
	                				monTrans.setCreditCount(0); 
	                			}
	                			
	                			if (isCredit) {                				
	                				monTrans.setCreditCount(1);  
	                				monTrans.setTotalCredits((Double.parseDouble(data[3])));                				
	                				monTrans.setDebitCount(0); 
	                			}
	                			
	                		}
						} else {
							
	            			if (!isCredit) {            				
	            				monTrans.setDebitCount(1);                				
	            				monTrans.setTotalDebits((Double.parseDouble(data[3])));
	            				monTrans.setCreditCount(0); 
	            			}
	            			
	            			if (isCredit) {            				
	            				monTrans.setCreditCount(1);                				
	            				monTrans.setTotalCredits((Double.parseDouble(data[3])));
	            				monTrans.setDebitCount(0);  
	            			} 
	                	
						}
					} else {						
						custMonTransMap = new HashMap<Long,Map<String, DailyTransaction>>();						                		            		
	            			if (!isCredit) {            				
	            				monTrans.setDebitCount(1);               				
	            				monTrans.setTotalDebits((Double.parseDouble(data[3])));            				
	            				monTrans.setCreditCount(0); 
	            			}
	            			
	            			if (isCredit) {            				
	            				monTrans.setCreditCount(1);             				
	            				monTrans.setTotalCredits((Double.parseDouble(data[3])));            				
	            				monTrans.setDebitCount(0);  
	            			}  
						
						}
					
					if(data.length == 6 && !data[5].isEmpty()) {
						if (data[5].equalsIgnoreCase("CASH")) {
							
							if (isCredit) {								
								double addAmt = (customerInfo.get(Long.parseLong(data[0]))).getCashCredit() + Double.parseDouble(data[3]);
								(customerInfo.get(Long.parseLong(data[0]))).setCashCredit(addAmt);								
								addAmt = (monTrans.getTotalCashCredits() + Double.parseDouble(data[3]));
								monTrans.setTotalCashCredits(addAmt);								
							} else {								
								double addAmt = (customerInfo.get(Long.parseLong(data[0]))).getCashDebit() + Double.parseDouble(data[3]);								
								(customerInfo.get(Long.parseLong(data[0]))).setCashDebit(addAmt);								
								addAmt = (monTrans.getTotalCashDebits() + Double.parseDouble(data[3]));
								monTrans.setTotalCashDebits(addAmt);								
							}
							
						}
					}  
					
					if (intMonth > 10) {    					
    					
						if(data.length == 6 && !data[5].isEmpty()) {
							if (data[5].equalsIgnoreCase("CASH")) {
								
		    					String custProfile = customerInfo.get(Long.parseLong(data[0])).getProfileCategory();
		    					double custCashDebit = (customerInfo.get(Long.parseLong(data[0]))).getCashDebit();    					
		    					
		    					long creditLmt = 0;
		    					int demontCreditLmt = 0;
		    					double demontCreditAmt = 0;
		    					
		    					if (custProfile.equalsIgnoreCase("HIGH")) {
		    						creditLmt = customerClassification.getHighCustCreditLimit();
		    						demontCreditAmt = transactionPatterns.getHighDemontizationCrAmount();
		    						demontCreditLmt = transactionPatterns.getHighDemontizationCreditCount();
		    						
		    						if (monTrans.getTotalCashCredits() > (custCashDebit * 1.2)) {
		        						(customerInfo.get(Long.parseLong(data[0]))).setFraud(true);
		        					}
		    						
		    					} else if (custProfile.equalsIgnoreCase("MEDIUM")) {
		    						creditLmt = customerClassification.getMediumCustCreditLimit();
		    						demontCreditAmt = transactionPatterns.getMediumDemontizationCrAmount();
		    						demontCreditLmt = transactionPatterns.getMediumDemontizationCreditCount();
		    						
		    						if (monTrans.getTotalCashCredits() > (custCashDebit * 1.15)) {
		        						(customerInfo.get(Long.parseLong(data[0]))).setFraud(true);
		        					}
		    						
		    					} else {    						
		    						creditLmt = customerClassification.getLowCustCreditLimit();
		    						demontCreditAmt = transactionPatterns.getLowDemontizationCrAmount();
		    						demontCreditLmt = transactionPatterns.getLowDemontizationCreditCount();
		    						
		    						if (monTrans.getTotalCashCredits() > (custCashDebit * 1.1)) {
		        						(customerInfo.get(Long.parseLong(data[0]))).setFraud(true);
		        					}
		    					}
							}
						}
    				} 
					
					
					
					monthTrans.put(month[1], monTrans);
        			
    				custMonTransMap.put(Long.parseLong(data[0]), monthTrans); 
    				
    				
	            }   

	        } catch (FileNotFoundException e) {
	            e.printStackTrace();
	        } catch (IOException e) {
	            e.printStackTrace();
	        } finally {
	            if (br != null) {
	                try {
	                    br.close();
	                } catch (IOException e) {
	                    e.printStackTrace();
	                }
	            }
	        }      
	        
	        if (custMonTransMap != null) {      	
	        	
	        	try {
					CSVUtils.WriteToFile(customerInfo, custMonTransMap);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        } else {
	        	System.out.println("Monthly Map is NULL");
	       }
	}
	
	public Map<Long,CustInfo> buildCustomerClassificationModel(CustomerCategory customerClassification) {
		csvFile = "C:\\projects\\Fraud Detection\\data\\custMaster.csv"; 
        
        br = null;
        Map<Long,CustInfo> customerInfo = new HashMap<Long,CustInfo>(); 
        try {

            br = new BufferedReader(new FileReader(csvFile));
            if ((line = br.readLine()) != null) {
            	// String[] headers = line.split(cvsSplitBy);
            }
            while ((line = br.readLine()) != null) {

                // use comma as separator
                String[] data = line.split(cvsSplitBy);
                
                if (customerInfo != null) {                	
                	if (customerInfo.containsKey(Long.parseLong(data[0])) ) {
                		;
                	} else {
                		CustInfo custDet = new CustInfo();
                		custDet.setCustomerName(data[1]);
                		custDet.setSalary(Long.parseLong(data[2]));
                		if (Long.parseLong(data[2]) <= customerClassification.getLowCustCreditLimit()) {
                			custDet.setProfileCategory("LOW");                			
                		} else if (Long.parseLong(data[2]) <= customerClassification.getMediumCustCreditLimit()) {
                			custDet.setProfileCategory("MEDIUM");
                		}else if (Long.parseLong(data[2]) <= customerClassification.getHighCustCreditLimit()) {
                			custDet.setProfileCategory("HIGH");
                		}
                		custDet.setFraud(false);
                		custDet.setCashDebit(1);
                		customerInfo.put(Long.parseLong(data[0]), custDet);
                	}
                } else {                	
                	
                	customerInfo = new HashMap<Long,CustInfo>();
                	
                	CustInfo custDet = new CustInfo();
            		custDet.setCustomerName(data[1]);
            		custDet.setSalary(Long.parseLong(data[2]));
            		if (Long.parseLong(data[2]) <= customerClassification.getLowCustCreditLimit()) {
            			custDet.setProfileCategory("LOW");                			
            		} else if ((Long.parseLong(data[2]) <= customerClassification.getMediumCustCreditLimit()) && (Long.parseLong(data[2]) > customerClassification.getLowCustCreditLimit())) {
            			custDet.setProfileCategory("MEDIUM");
            		}else if ((Long.parseLong(data[2]) <= customerClassification.getHighCustCreditLimit()) && (Long.parseLong(data[2]) > customerClassification.getMediumCustCreditLimit())) {
            			custDet.setProfileCategory("HIGH");
            		}
            		custDet.setFraud(false);
            		custDet.setCashDebit(1);
            		customerInfo.put(Long.parseLong(data[0]), custDet);
                }
                
                
            }
            
            
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        
        if (customerInfo != null) {
        	for (Entry<Long, CustInfo> entry : customerInfo.entrySet()) {        		
        		CustInfo custSum = (CustInfo) entry.getValue() ;   
        	}
        } else {
        	System.out.println("Map is NULL");
        }
        return customerInfo;
	}	
	
	public Patterns buildFraudPatterns (CustomerCategory customerClassification) {
		transactionPatterns = new Patterns();
		transactionPatterns.setDemontizationCrAmount(250000);
		
		transactionPatterns.setHighCreditCount(10);
		transactionPatterns.setHighDemontizationCreditCount(15);
		transactionPatterns.setHighDemontizationCrAmount(customerClassification.getHighCustCreditLimit() * (1/100));
		
		transactionPatterns.setLowCreditCount(2);
		transactionPatterns.setLowDemontizationCreditCount(4);
		transactionPatterns.setLowDemontizationCrAmount(customerClassification.getLowCustCreditLimit() * (1/100));
		
		transactionPatterns.setMediumCreditCount(4);
		transactionPatterns.setMediumDemontizationCreditCount(6);
		transactionPatterns.setMediumDemontizationCrAmount(customerClassification.getMediumCustCreditLimit() * (1/100));	
		
		return transactionPatterns;
	}
	
	public CustomerCategory buildCustomerCategory () {
		customerClassification = new CustomerCategory();
		customerClassification.setHighCustCreditLimit(2000000);
		customerClassification.setMediumCustCreditLimit(1000000);
		customerClassification.setLowCustCreditLimit(600000);
		
		return customerClassification;
	}
	
}
