package com.cidic.fontdesign.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

	public static Date stringToDate(String dateString){
		Date date = null;
	    try  
	    {  
	        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  
	        date = sdf.parse(dateString);  
	    }  
	    catch (ParseException e)  
	    {  
	        System.out.println(e.getMessage());  
	    }
		return date;  
	}
	
	 public static  String formatDate(Date date)throws ParseException{
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date);
    }
	 
	 public static Date parse(String strDate) throws ParseException{
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        
        return sdf.parse(strDate);
    }
}
