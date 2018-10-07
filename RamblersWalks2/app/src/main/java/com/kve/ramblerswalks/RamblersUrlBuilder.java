package com.kve.ramblerswalks;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import android.util.Log;


public class RamblersUrlBuilder {

	//	Take all the info from intent and make sense of it and return a list of RamblersUrls
	static final List<String> weekdayNames =  Arrays.asList("Monday", "Tuesday", "Wednesday", "Thursday", "Friday");
	static final List<String> weekendNames =  Arrays.asList("Saturday", "Sunday");

	ArrayList<String> chosenWeekdays;
	String endDay;
	String endMonthNo;
	
	private ArrayList<String> urlList;
	
	public RamblersUrlBuilder(Map<String,String> requestedInfo)
	{
		super();
		
		modifyForButtonPressed(requestedInfo);
		
		
		urlList = new ArrayList<String>();
/*		
		for (String weekday : chosenWeekdays)
		{
			RamblersUrl url = new RamblersUrl(
					requestedInfo.get("PostCode"), 
					requestedInfo.get("StartDay"), 
					endDay, 
					requestedInfo.get("StartMonthNo"), 
					endMonthNo, 
					weekday,
					requestedInfo.get("Distance"));
			urlList.add(url.getUrl());
		}
*/
		RamblersUrl url = new RamblersUrl(
				requestedInfo.get("PostCode"), 
				chosenWeekdays,
				requestedInfo.get("Distance"));
		urlList.add(url.getUrl());
			
				
	}
	
	private void modifyForButtonPressed(Map<String,String> requestedInfo)
	{
		String buttonPressed = requestedInfo.get("ButtonPressed");
		/////			get a week and a month from now
		Calendar futureDate = getFutureDate("month");
		endDay		= ""	+ futureDate.get(Calendar.DATE);
		endMonthNo	= ""	+ (futureDate.get(Calendar.MONTH) + 1);
		
		chosenWeekdays = new ArrayList<String>();
		if (buttonPressed.equals("Weekend"))
		{
			chosenWeekdays.addAll(weekendNames);
		}
		else	if (buttonPressed.equals("Weekdays"))
		{
			chosenWeekdays.addAll(weekdayNames);
		}
		else	if (buttonPressed.equals("Evenings")  || buttonPressed.equals("EveryDay"))
		{
			chosenWeekdays.add("All");
		}
		else	if (buttonPressed.equals("ThisWeek"))
		{
			chosenWeekdays.add("All");
			futureDate = getFutureDate("week");		
			endDay		= ""	+ futureDate.get(Calendar.DATE);
			endMonthNo	= ""	+ (futureDate.get(Calendar.MONTH) + 1);
		}
		else
		{
			chosenWeekdays = null;
			chosenWeekdays = new ArrayList<String>( Arrays.asList(requestedInfo.get("Weekdays").split(", |,|;")));
			endDay		=	requestedInfo.get("EndDay");
			endMonthNo	= 	requestedInfo.get("EndMonthNo");
		}
	}
	
	
	private Calendar getFutureDate(String timeSpan)
	{
		Calendar futureDate = Calendar.getInstance();
		int daysThisMonth = futureDate.getActualMaximum(Calendar.DAY_OF_MONTH);
		if (timeSpan.equals("month"))
		{
			futureDate.add(Calendar.DATE, daysThisMonth); 
		}
		if (timeSpan.equals("week"))
		{
			futureDate.add(Calendar.DATE, 7); 
		}
		return futureDate;
	}
	
	public ArrayList<String> getUrlList()
	{
		return urlList;
	}
	
}
