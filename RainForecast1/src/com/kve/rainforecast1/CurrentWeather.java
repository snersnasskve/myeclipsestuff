package com.kve.rainforecast1;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class CurrentWeather extends Activity {
	/*
	 * 
     *	Just about every object property in the response is optional. 
     *		In fact, a request with no data to return will return a nearly empty object, rather than a failure. 
     *		Robust code will check for the presence of required parameters before using them, and will fail gracefully if they are not present.
   	 *	All numeric properties are real numbers, except for UNIX timestamps, which are (signed) integers.
     *	Summaries on the hourly data block actually only cover up to a maximum of 24 hours, 
     *		rather than the full time period in the data block. 
     *	Summaries and icons on daily data points actually cover the period from 4AM to 4AM, 
     *		rather than the stated time period of midnight to midnight. We found that the summaries so generated were less awkward.
     *	The Forecast Data API supports HTTP compression. 
     *		We heartily recommend using it, as it will make responses much smaller over the wire. 
     *		To enable it, simply add an Accept-Encoding: gzip header to your request. 
     *		(Most HTTP client libraries wrap this functionality for you, please consult your library’s documentation for details. 
     *		Be advised that we do not support such compression over HTTP/1.0 connections.)
	 */
	
	//	Please I want time until precipitation

	ImageView ivCurIcon;
	TextView tvCurSummary;
	TextView tvCurPrecipIntensity;
	TextView tvCurPrecipProbability;
	TextView tvCurTemperature;
	TextView tvCurWind;
	TextView tvCurTimeTilSunset;
	TextView tvCurTimeTilPrecip;
	HorizontalScrollView hsvCurActivityIcons;
	LinearLayout llCurIcontainer;
	
	ForecastReader	 forecastReader;
	
	boolean isDayTime;
    
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
	    setContentView(R.layout.currently);
	    ivCurIcon				= (ImageView)findViewById(R.id.ivCurIcon);
	    tvCurSummary			= (TextView) findViewById(R.id.tvCurSummary);
		tvCurPrecipIntensity	= (TextView) findViewById(R.id.tvCurPrecipIntensity);
		tvCurPrecipProbability	= (TextView) findViewById(R.id.tvCurPrecipProbability);
		tvCurTemperature		= (TextView) findViewById(R.id.tvCurTemperature);
		tvCurWind				= (TextView) findViewById(R.id.tvCurWind);
		tvCurTimeTilSunset		= (TextView) findViewById(R.id.tvCurTimeTilSunset);
		tvCurTimeTilPrecip		= (TextView) findViewById(R.id.tvCurTimeTilPrecip);
		hsvCurActivityIcons		= (HorizontalScrollView)findViewById(R.id.hsvCurActivityIcons);
		llCurIcontainer			= (LinearLayout) findViewById(R.id.llCurIcontainer);
		
		forecastReader	 = new ForecastReader();
		  	
		Intent receivedIntent = getIntent();
		String jsonData = receivedIntent.getExtras().getString("jsonData");
		
		HashMap <String, String> currentInfo = forecastReader.parseCurrentInfo(jsonData);
		
		tvCurSummary.			setText(currentInfo.get("summary"));
		tvCurPrecipIntensity.	setText(currentInfo.get("precipIntensity"));
		tvCurPrecipProbability.	setText(currentInfo.get("precipProbability"));
		tvCurTemperature.		setText(currentInfo.get("temperature"));
		tvCurWind.				setText(currentInfo.get("windSpeed"));
		
		tvCurTimeTilSunset.		setText(formatTime(Long.parseLong(currentInfo.get("timeTilSunset"))));
		
		//	timeTillPrecip
		String timeTilPrecipString = "None forecast";
		long timeTilPrecip = forecastReader.timeTilPrecip(jsonData, false);
		if (timeTilPrecip > 0)
		{
			timeTilPrecipString = formatTime(timeTilPrecip);
		}
		tvCurTimeTilPrecip.  	setText(timeTilPrecipString);
		
		Log.i("Icon", currentInfo.get("icon"));
		//	Icon
		String			iconName = currentInfo.get("icon").replaceAll("-", "_");
		int iconId 	= 	getResources().getIdentifier(iconName, "drawable", getPackageName());
		ivCurIcon.		setImageResource(iconId);	
		ivCurIcon.		setContentDescription(iconName);
		
		isDayTime	=	forecastReader.isDayTime(jsonData);
		
		setWeatherActivityIcons(jsonData);
		
	}


	private void setWeatherActivityIcons(String jsonData) {
		//	Call Json will it rain currently if not, add Washing icon
		ArrayList <String> qualIcons = new ArrayList <String> ();
		
		if (okToBraai(jsonData))
		{
			qualIcons.add("campfire");
		}
		if (okToGoHiking(jsonData))
		{
			qualIcons.add("hiking");
		}
		if (okToHangWashing(jsonData))
		{
			qualIcons.add("washing");
		}
		if (okToUseUmbrella(jsonData))
		{
			qualIcons.add("umbrella");
		}
			
		inflateWeatherActivityIcons(qualIcons);
	}


	private void inflateWeatherActivityIcons(ArrayList<String> qualIcons) {
		 LayoutInflater layoutInflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		 llCurIcontainer.removeAllViews();
		 for (String iconName : qualIcons)
		 {
			 int iconId = getResources().getIdentifier(iconName, "drawable", getPackageName());
			 View convertView = layoutInflater.inflate(R.layout.icon_gallery, null);
	       
			 final ImageView img = (ImageView) convertView.findViewById(R.id.ivIconGalleryItem);
			 img.setImageResource(iconId);
			 llCurIcontainer.addView(convertView);
		 }
        	    
	}
	
	
	///////////////////////////////////////////
	//	Weather Activity Checkers			 //
	///////////////////////////////////////////

	private boolean okToHangWashing(String jsonData)
	{
		boolean isItOK = false;
		if (isDayTime && !forecastReader.dataContainsWeatherword("rain", "minutely", jsonData) 
				&& (forecastReader.timeTilSunset(jsonData) > 60))
		{
			isItOK = true;
		}
		return isItOK;
	}

	private boolean okToGoHiking(String jsonData)
	{
		boolean isItOK = false;
		if (isDayTime && !forecastReader.dataContainsWeatherword("rain", "hourly", jsonData)
				&& (forecastReader.timeTilSunset(jsonData) > 120))
		{
			isItOK = true;
		}
		return isItOK;
	}

	private boolean okToUseUmbrella(String jsonData)
	{
		boolean isItOK = false;
		if (forecastReader.dataContainsWeatherword("rain", "minutely", jsonData))
		{
			isItOK = true;
		}
		return isItOK;
	}

	private boolean okToBraai(String jsonData)
	{
		boolean isItOK = false;
		if (!forecastReader.dataContainsWeatherword("rain", "minutely", jsonData))
		{
			isItOK = true;
		}
		return isItOK;
	}
	
	
	private String formatTime(long totalMinutes)
	{
		String timeString = "" + totalMinutes;
		int hours = (int) totalMinutes / 60;
		int mins  = (int) totalMinutes % 60;
		DecimalFormat myFormatter = new DecimalFormat("00");
		String formattedMins = myFormatter.format(mins);
		timeString = "" + hours + ":" + formattedMins + " hours";
		return timeString;
	}

	public void displayMinutely(){
		//	dead button
	}

   


}
