package com.capgemini.hackathon.automation.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class DateUtil {
	private static final String [] months={"JAN","FEB","MAR","APR","MAY","JUN","JUL","AUG","SEP","OCT","NOV","DEC"};
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
	
	public static String getMonthYear(Date date) {
		String monthYear=null;
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH);
		monthYear=months[month] + "_"+ year;
		return monthYear;
	}
	
	public static boolean isDateCurrentMonth(Date date, Integer currentMonthIndex, Integer currentYear) {
		boolean flag=false;
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH);
		flag = (currentYear == year) && (currentMonthIndex == month); 
		return flag;
	}
	
	public static int getMonth(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int month = calendar.get(Calendar.MONTH);
		return month;
	}
	
	public static int getYear(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int year = calendar.get(Calendar.YEAR);
		return year;
	}
		
}
