package com.capgemini.hackathon.automation.util;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.capgemini.hackathon.automation.model.CustInfo;
import com.capgemini.hackathon.automation.model.DailyTransaction;

public class CSVUtils {

    private static final char DEFAULT_SEPARATOR = ',';

    public static void writeLine(Writer w, List<String> values) throws IOException {
        writeLine(w, values, DEFAULT_SEPARATOR, ' ');
    }

    public static void writeLine(Writer w, List<String> values, char separators) throws IOException {
        writeLine(w, values, separators, ' ');
    }


    private static String followCVSformat(String value) {

        String result = value;
        if (result.contains("\"")) {
            result = result.replace("\"", "\"\"");
        }
        return result;

    }

    public static void writeLine(Writer w, List<String> values, char separators, char customQuote) throws IOException {

        boolean first = true;

        if (separators == ' ') {
            separators = DEFAULT_SEPARATOR;
        }

        StringBuilder sb = new StringBuilder();
        for (String value : values) {
            if (!first) {
                sb.append(separators);
            }
            if (customQuote == ' ') {
                sb.append(followCVSformat(value));
            } else {
                sb.append(customQuote).append(followCVSformat(value)).append(customQuote);
            }

            first = false;
        }
        sb.append("\n");
        w.append(sb.toString());

    }
    
    public static void WriteToFile(Map<Long,CustInfo> customerInfo , Map<Long,Map<String, DailyTransaction>> custMonTransMap) throws Exception {

    	String csvFile = "C:\\projects\\Fraud Detection\\data\\report.csv";
        FileWriter writer = new FileWriter(csvFile);
        
        CSVUtils.writeLine(writer,  Arrays.asList("Month" , "Year" , "CustomerID", "Total Credits" , "Credit Count", "Total Debits" , "Debit Count"));
        
        if (custMonTransMap != null) {        	
        	String month;
        	String year;
        	for (Entry<Long, Map<String, DailyTransaction>> entry : custMonTransMap.entrySet()) {
        		System.out.println("Customer ID : " + entry.getKey());
        		System.out.println("Is FRAUD : "  +customerInfo.get(entry.getKey()).isFraud());
        		Map<String, DailyTransaction> monthTrans = (Map<String, DailyTransaction>) entry.getValue() ;
        		
        		for (Entry<String, DailyTransaction> entry1 : monthTrans.entrySet()) {
        			System.out.println("Month : " + entry1.getKey());
        			month = entry1.getKey();
        			DailyTransaction monTrans =  (DailyTransaction) (entry1.getValue()) ;
        			System.out.println("Total Credits : " + monTrans.getTotalCredits() + " Credit Count : " + monTrans.getCreditCount()
        			+ " Total Debits : " + monTrans.getTotalDebits() + " Debit Count : " + monTrans.getDebitCount());
        			
        			System.out.println("Total Cash Credits : " + monTrans.getTotalCashCredits() + " Total Debits : " + monTrans.getTotalCashDebits());	        			
        			System.out.println(" Overall Total Cash Credits : " + customerInfo.get(entry.getKey()).getCashCredit() + " ; Overall Total Debits : " + customerInfo.get(entry.getKey()).getCashDebit());
        			
        			if (Integer.parseInt(month) > 12 ) {
        				year = "2017";
        				month = String.valueOf((Integer.parseInt(month)) - 12);
        			} else {
        				year = "2016";
        			}
        			
        			CSVUtils.writeLine(writer, Arrays.asList(month, year,
        					(String.valueOf(entry.getKey())), (String.valueOf(monTrans.getTotalCredits())) , (String.valueOf(monTrans.getCreditCount()))
        					, (String.valueOf(monTrans.getTotalDebits())) , (String.valueOf(monTrans.getDebitCount()))
        					));
        		}
        		
        		
        	}
        } else {
        	System.out.println("Monthly Map is NULL");
       }        
        writer.flush();
        writer.close();
        
        //
        
        csvFile = "C:\\projects\\Fraud Detection\\data\\fraudAlert.csv";
        writer = new FileWriter(csvFile);
        
        CSVUtils.writeLine(writer,  Arrays.asList("CustomerID", "Category", "Salary" , "Is Fraud"));
        
        if (custMonTransMap != null) {        	
        	String month;
        	String year;
        	for (Entry<Long, Map<String, DailyTransaction>> entry : custMonTransMap.entrySet()) {
        		System.out.println("Customer ID : " + entry.getKey());
        		System.out.println("Is FRAUD : "  +customerInfo.get(entry.getKey()).isFraud());
        			
        		CSVUtils.writeLine(writer, Arrays.asList((String.valueOf(entry.getKey())), (String.valueOf(customerInfo.get(entry.getKey()).getProfileCategory())),
        				(String.valueOf(customerInfo.get(entry.getKey()).getSalary())), 
        				(String.valueOf(customerInfo.get(entry.getKey()).isFraud()))
        					));
        		}
        		
        		
        } else {
        	System.out.println("Monthly Map is NULL");
       }        
        writer.flush();
        writer.close();
        

    }

}