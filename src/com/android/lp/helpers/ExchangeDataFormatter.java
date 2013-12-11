package com.android.lp.helpers;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ExchangeDataFormatter {

	
	public String convertClientDateToServerDate( int year, int month, int day ){
		//return new StringBuilder(10).append(year).append("-").append(month).append("-").append(day).toString(); 

		final Calendar calendar = Calendar.getInstance();
		
		calendar.set(year, month, day);

		return new SimpleDateFormat("yyyy-MM-dd").format( calendar.getTime() );
	}
	
	public String convertClientTimeToServerTime( int hour, int minute, int seconds ){
		return new StringBuilder(5).append(hour).append(":").append(minute).toString(); 
	}	
}
