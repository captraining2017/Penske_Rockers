package com.capgemini.hackathon.automation.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DateUtil {
	
	public static List<Date> getMonthDateList(int month, Date date) {
		List<Date> eachMonthDateList = new ArrayList<>();
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.DAY_OF_MONTH, 1); 
		while (month==cal.get(Calendar.MONTH)) {
			eachMonthDateList.add(cal.getTime());
		  cal.add(Calendar.DAY_OF_MONTH, 1);
		}
		return eachMonthDateList;
	}
	
	public static Date getFirstMonthOfDate(int year, int month) {
		Calendar c = Calendar.getInstance();      
		c.set(year,month,1); //------>  
		c.set(Calendar.DAY_OF_MONTH, c.getActualMinimum(Calendar.DAY_OF_MONTH));  
		return c.getTime();
	}
	
	public static Date getFormatedDate(String dateFormat, Date txnDate) throws ParseException {
		DateFormat formatter = new SimpleDateFormat(dateFormat);
		 String formatDateStr=formatter.format(txnDate);
		return formatter.parse(formatDateStr);
	}
	
	public static Date getFormatedDate(String dateFormat, String txnDate) throws ParseException {
		DateFormat formatter = new SimpleDateFormat(dateFormat);
		return formatter.parse(txnDate);
	}
		
}
