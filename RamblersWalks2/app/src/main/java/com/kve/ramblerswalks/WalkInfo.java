package com.kve.ramblerswalks;

import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.R.bool;

public class WalkInfo  {
	
	//private String title 		= "";
	//private String link 		= "";
	//private String description 	= "";
	//private String guid 		= "";
	private Date   date ;
	
	private Double longitude 	= 0.0;
	private Double latitude 	= 0.0;
	
	private String dateString		= "";
	private String difficulty		= "";
	private String distanceKm		= "";
	private String distanceMiles	= "";
	private String exact			= "";
	private String group			= "";
	private String id				= "";
	private String summary			= "";
	private String timeString		= "";
	private String title			= "";
	private String url				= "";


	
	public WalkInfo(Double longitude, Double latitude, 
			String dateString, String difficulty, String distanceKm, String distanceMiles, String exact, 
			String group, String id, String summary, String timeString, String title, String url)
	{
		this.longitude 	= longitude;
		this.latitude 	= latitude;
		
		this.dateString		= dateString;
		this.difficulty		= difficulty;
		this.distanceKm		= distanceKm;
		this.distanceMiles	= distanceMiles;
		this.exact			= exact;
		this.group			= group;
		this.id				= id;
		this.summary		= summary;
		this.timeString		= timeString;
		this.title			= title;
		this.url			= url;
		
		setDate(dateString);
	}
	//	When you know your fields use Source in menu to make getters and setters


	public WalkInfo() {
		this.longitude 	= 0.0;
		this.latitude 	= 0.0;
		
		this.dateString		= "";
		this.difficulty		= "";
		this.distanceKm		= "";
		this.distanceMiles	= "";
		this.exact			= "";
		this.group			= "";
		this.id				= "";
		this.summary		= "";
		this.timeString		= "";
		this.title			= "";
		this.url			= "";
	}

	public boolean distanceInBounds(String boundsString)
	{
		boolean inBounds = false;
		String[] limits = boundsString.split("-");
		if (getDistanceMiles().equals(""))
		{
			//	Who knows - but we cannot filter it out so return true
			inBounds = true;
		}
		else
		{ 
			Double distanceNum = Double.parseDouble(getDistanceMiles());
			{
				if (2 == limits.length  && 
						distanceNum >= Double.parseDouble(limits[0]) && distanceNum <= Double.parseDouble(limits[1]) )
				{
					inBounds = true;
				}
			}
		}
		return inBounds;
	}

	
	public String getTitle() {
		return (this.title);
	}

	public String getDescription()
	{
		return getGroup() + " - " + getDateString() + "\n"
	+ getDistanceMiles() + " miles / "+ getDistanceKm() + " km\n" 
				 + getSummary();
	}
	
	
	public String getLink()
	{
		return getUrl();
	}

	//////			Private

	private void setTitle(String title) {
		this.title = title;
	}



	
	private void setDate(String textDate)
	{
		/*
		Calendar cal = Calendar.getInstance();
		
		String[] sentenceParts = aTitle.split(":| ");
		Pattern integerPattern = Pattern.compile("(\\d*)");
		Matcher matcher = integerPattern.matcher(sentenceParts[0]);
		int dayNo = -1;
		
		if (matcher.find())
			{
			dayNo = Integer.parseInt(matcher.group(1));
			}
	
		cal.set(Calendar.DATE, dayNo);
		cal.set(Calendar.MONTH,getIntForMonth(sentenceParts[1]));
	*/
		try {
			this.date = new SimpleDateFormat("E, d MMMM yyyy", Locale.ENGLISH).parse(textDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			this.date = null;
		}
		
	}
	
	
	private int getIntForMonth(String monthString) {
		int monthNo = -1;
	    DateFormatSymbols dfs = new DateFormatSymbols();
	    String[] months = dfs.getMonths();
	    for (int i = 0 ; i < 12 ; i++)
	    {
	    	if (monthString.equalsIgnoreCase(months[i]))
	    	{
	    		monthNo = i;
	    	}
	    }
	    return monthNo;
	}


	/*
	@Override
	public int compareTo(WalkInfo another) {
        int lastCmp = date.compareTo(another.date);
        return (lastCmp != 0 ? lastCmp : date.compareTo(another.date));
	}
	*/

	private Double getLongitude() {
		return longitude;
	}


	private void setLongitude(Double longitude) {
		this.longitude = longitude;
	}


	private Double getLatitude() {
		return latitude;
	}


	private void setLatitude(Double latitude) {
		this.latitude = latitude;
	}


	private String getDateString() {
		return dateString;
	}


	private void setDateString(String dateString) {
		this.dateString = dateString;
		this.setDate(dateString);
	}


	private String getDifficulty() {
		return difficulty;
	}


	private void setDifficulty(String difficulty) {
		this.difficulty = difficulty;
	}


	private String getDistanceKm() {
		return distanceKm;
	}


	private void setDistanceKm(String distanceKm) {
		this.distanceKm = distanceKm;
	}


	private String getDistanceMiles() {
		return distanceMiles;
	}


	private void setDistanceMiles(String distanceMiles) {
		this.distanceMiles = distanceMiles;
	}


	private String getExact() {
		return exact;
	}


	private void setExact(String exact) {
		this.exact = exact;
	}


	private String getGroup() {
		return group;
	}


	private void setGroup(String group) {
		this.group = group;
	}


	private String getId() {
		return id;
	}


	private void setId(String id) {
		this.id = id;
	}


	private String getSummary() {
		return summary;
	}


	private void setSummary(String summary) {
		this.summary = summary;
	}


	private String getTimeString() {
		return timeString;
	}


	private void setTimeString(String timeString) {
		this.timeString = timeString;
	}


	private String getUrl() {
		return url;
	}


	private void setUrl(String url) {
		this.url = url;
	}



}
