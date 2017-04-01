package com;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class testDate {

	/*public static void main(String[] args) {
		SimpleDateFormat f = new SimpleDateFormat("dd-MMM-yyyy");
	    String d = "12-OCT-2010";
	    try {
	        Date formatted = f.parse(d);
	        Date sysDate = new Date();
	        System.out.println(formatted);
	        System.out.println(sysDate);
	        if(formatted.before(sysDate)){
	            System.out.println("Formatted Date is older");
	        }
	    } catch (ParseException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
	    }
	}
	*/
	
	public static void main(String[] args) throws Exception {
        String dateInputPattern =  "dd-MMM-yyyy"; //"yyyy-MMM-dd"; "yyyy-MM-dd"; // numeric 2 digit month
        String dateTargetPattern = "dd-MMM-yyyy"; //yyyy-MMM-dd"; // For 3 char month name
        String dateString = "1-Apr-2017"; //2017-Apr-1";

        patternTest( dateInputPattern, dateString, dateTargetPattern );

        System.out.println();

        // day of month first and then 2 digit month
        dateInputPattern = "yyyy-dd-MM";  
        dateString = "2014-21-03";
        dateTargetPattern = "yyyy-MMMM-dd, EEEE"; // for Full month name

        patternTest( dateInputPattern, dateString, dateTargetPattern );
    } // psvm( ... )

    public static void 
    patternTest( String dateInputPattern, 
                 String dateString, 
                 String dateTargetPattern ) throws Exception {
        java.text.SimpleDateFormat sdf = 
            new java.text.SimpleDateFormat( dateInputPattern );
        java.util.Date date = sdf.parse( dateString );

        System.out.println( "Date Pattern: " + dateInputPattern );
        System.out.println( "Date String : " + dateString );
        System.out.println( "Date Value  : " + date );
        sdf.applyPattern( dateTargetPattern );
        System.out.println( "Target Pattern: " + dateTargetPattern );
        System.out.println( "Pattern based Date Value: " + sdf.format(date) );

        java.sql.Date sqlDate = new java.sql.Date( date.getTime() );
        System.out.println( "But, SQL Date: " + sqlDate );
    } // patternTest( s, s, s )
} // end of class SimpleDateFormat_Example


