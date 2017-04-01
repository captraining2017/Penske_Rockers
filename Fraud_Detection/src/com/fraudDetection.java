package com;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class fraudDetection {

	public static void main(String[] args) {
		
		
		
        String csvFile = "D:\\Users\\irajasek\\Documents\\testData.csv";
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",";
        
        SimpleDateFormat sdf = new SimpleDateFormat ("dd-MMM-yyyy"); //("dd-M-yyyy hh:mm:ss");
        Date date = null;
        
        Map<Long,Map<String, dailyTransaction>> custTransMap = null; 

        try {

            br = new BufferedReader(new FileReader(csvFile));
            if ((line = br.readLine()) != null) {
            	// String[] headers = line.split(cvsSplitBy);
            }
            while ((line = br.readLine()) != null) {

                // use comma as separator
                String[] data = line.split(cvsSplitBy);

                System.out.println("CustomerId [code= " + data[0] + " , Credit=" + data[4] + "]");
                System.out.println("Date [code= " + data[2]);          
                date = null;
				try {
					date = sdf.parse(data[2]);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				sdf.applyPattern("dd-MM-yyyy");
				System.out.println("Date converted [code= " + date + "formatted value : " +sdf.format(date));
				String dateStr = sdf.format(date);
			
                // System.out.println(date); //Tue Aug 31 10:20:56 SGT 1982
				System.out.println(custTransMap);
                if (custTransMap != null) {
                	System.out.println("custTransMap != null");
                	HashMap<String, dailyTransaction> dailyTrans = new HashMap<String, dailyTransaction>();
                	if (custTransMap.containsKey(dateStr) ) {
                		dailyTrans = (HashMap<String, dailyTransaction>) custTransMap.get(dateStr);
                		if (dailyTrans.containsKey(date)) {
                			dailyTransaction trans = dailyTrans.get(date);
                			if (!data[3].isEmpty()) {
                				System.out.println("trans.getDebitCount() : " +trans.getDebitCount());
                				trans.setDebitCount(trans.getDebitCount() + 1);
                				System.out.println("trans.getTotalDebits() : " +trans.getTotalDebits());
                				trans.setTotalDebits(trans.getTotalDebits() + (Double.parseDouble(data[3])));
                			}
                			
                			if (!data[4].isEmpty()) {
                				System.out.println("trans.getCreditCount() : " +trans.getCreditCount());
                				trans.setCreditCount(trans.getCreditCount() + 1);
                				System.out.println("trans.getTotalCredits() : " +trans.getTotalCredits());
                				trans.setTotalCredits(trans.getTotalCredits() + (Double.parseDouble(data[4])));
                			}
                			
                		} else {
                			dailyTransaction trans =  new dailyTransaction();
                			
                			if (!data[3].isEmpty()) {
                				trans.setDebitCount(1);
                				trans.setTotalDebits((Double.parseDouble(data[3])));
                			}
                			
                			if (!data[4].isEmpty()) {
                				trans.setCreditCount(1);
                				trans.setTotalCredits((Double.parseDouble(data[4])));
                			}
                			
                			dailyTrans.put(dateStr, trans);
                		}
                	}
                	
                } else {
                	System.out.println("custTransMap = null");
                	custTransMap = new HashMap<Long,Map<String, dailyTransaction>>();
                		HashMap<String, dailyTransaction> dailyTrans = new HashMap<String, dailyTransaction>();
                		dailyTransaction trans =  new dailyTransaction();
                		System.out.println("Debit data : " +data[3]);
            			if (!data[3].isEmpty()) {
            				System.out.println("new Debit data");
            				trans.setDebitCount(1);                				
            				trans.setTotalDebits((Double.parseDouble(data[3])));
            			}
            			
            			if (!data[4].isEmpty()) {
            				System.out.println("new credit data");
            				trans.setCreditCount(1);                				
            				trans.setTotalCredits((Double.parseDouble(data[4])));
            			}              			
                		
                			dailyTrans.put(dateStr, trans);
                			
                			custTransMap.put(Long.parseLong(data[0]), dailyTrans);                		
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
        if (custTransMap != null) {
        	for (Entry<Long, Map<String, dailyTransaction>> entry : custTransMap.entrySet()) {
        		System.out.println("Key : " + entry.getKey() + " Value : " + entry.getValue());
        		Map<String, dailyTransaction> dailyTrans = (HashMap<String, dailyTransaction>) entry.getValue() ;
        		for (Entry<String, dailyTransaction> entry1 : dailyTrans.entrySet()) {
        			System.out.println("Key : " + entry1.getKey() + " Value : " + entry1.getValue());
        			dailyTransaction trans =  (dailyTransaction) ((Object)entry.getValue()) ;
        			System.out.println("Total Credits : " + trans.getTotalCredits() + "Credit Count : " + trans.getCreditCount()
        			+ "Total Debits : " + trans.getTotalDebits() + "Debit Count : " + trans.getDebitCount());
        		}
        	}
        } else {
        	System.out.println("Map is NULL");
        }
    }


}
