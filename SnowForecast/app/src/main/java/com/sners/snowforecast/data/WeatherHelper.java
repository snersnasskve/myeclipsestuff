package com.sners.snowforecast.data;

import com.sners.snowforecast.data.IntervalData;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Locale;



public class WeatherHelper {

	public static final String MM_HR = " mm/hr";
	public static final String PERCENT = " %";

	private static final DecimalFormat tempFormat = new DecimalFormat("0.0");
	Double beaufortScaleUppers[] = {1.0, 3.0, 7.0, 12.0, 17.0, 24.0, 30.0, 38.0, 46.0, 54.0, 63.0, 73.0};

	String[] knownWeatherWords = {"Snow", "snow", "Rain", "rain", "Drizzle", "drizzle",
			"Flurry", "flurry", "Hail", "hail", "Storm", "storm"};
	
	public WeatherHelper() {
		
	}
	
	public int periodWhenValueExceededPrecipIntensity(ArrayList<IntervalData> intervalData, double minValue)
	{
		int periodFound = -1;
		boolean precipExceedsMin = false;
	
			int intervalCounter = 0;
			while (intervalCounter < intervalData.size() && !precipExceedsMin)
			{
				Float 		fieldValue = intervalData.get(intervalCounter).getPrecipIntensityNum();
				if (fieldValue > minValue)
				{
					precipExceedsMin = true;
					periodFound = intervalCounter;
				}
				intervalCounter++;
			}
		return periodFound;
	}

	public boolean dataContainsKeyword(ArrayList<IntervalData> intervalData, String keyword)
	{
		boolean wordFound = false;

		int intervalCounter = 0;
		while (intervalCounter < intervalData.size() && !wordFound)
		{
			if (intervalData.get(intervalCounter).getSummary().toLowerCase(Locale.ENGLISH).contains(keyword))
			{
				wordFound = true;
				break;
			}
			intervalCounter++;
		}
		return wordFound;
	}

	public int intervalCounterForKeyword(ArrayList<IntervalData> intervalData, String keyword)
	{
		boolean wordFound = false;

		int intervalCounter = 0;
		while (intervalCounter < intervalData.size() && !wordFound)
		{
			if (intervalData.get(intervalCounter).getSummary().toLowerCase(Locale.ENGLISH).contains(keyword.toLowerCase(Locale.ENGLISH)))
			{
				wordFound = true;
				break;
			}
			else if (null != intervalData.get(intervalCounter).getPrecipType() &&
					intervalData.get(intervalCounter).getPrecipType().toLowerCase(Locale.ENGLISH).contains(keyword.toLowerCase(Locale.ENGLISH)))
			{
				wordFound = true;
				break;
			}
			intervalCounter++;
		}
		if (!wordFound)
		{
			intervalCounter = -1;
		}
		return intervalCounter;
	}

	public int windSpeedToBeaufort(Double windSpeed)
	{
		int beaufortValue = 0;
		for (int scaleCounter = 0 ; scaleCounter < beaufortScaleUppers.length ; scaleCounter++)
		{
			if (windSpeed < beaufortScaleUppers[scaleCounter])
			{
				beaufortValue = scaleCounter;
				break;
			}
		}
		return beaufortValue;
	}
	
	////////////////////////////////////////////////////////////////////////////////
	//	Format Strings
	////////////////////////////////////////////////////////////////////////////////

	public Double fahrenheitToCelsius(String fahrenheit)
	{
		Double fahr = Double.parseDouble(fahrenheit);
		Double cels = (fahr - 32)*5/9;
		return cels ;
	}

	public String precipIntensityToMilsFormatted(String inchesString)
	{
		Float mils = precipIntensityToMils(inchesString);
		return tempFormat.format(mils) + MM_HR;
	}

	public Float precipIntensityToMils(String inchesString)
	{
		Float inches = Float.parseFloat(inchesString);
		return (inches * 2.54f);
	}


	public String probabilityToPercent(String decimalNo)
	{
		Double dec = Double.parseDouble(decimalNo);
		Double perc = (dec * 100);
		return tempFormat.format(perc) + PERCENT;
	}
	
	public String formatTime(long totalMinutes)
	{
		String timeString = "" + totalMinutes;
		int hours = (int) totalMinutes / 60;
		int mins  = (int) totalMinutes % 60;
		DecimalFormat myFormatter = new DecimalFormat("00");
		String formattedMins = myFormatter.format(mins);
		timeString = "" + hours + ":" + formattedMins + " hours";
		return timeString;
	}

	public ArrayList <String> weatherWordsFromString(String anyString)
	{
		ArrayList <String> foundWords = new ArrayList <String>();
		for (int wCounter = 0 ; wCounter < knownWeatherWords.length ; wCounter++)
		{
			if (anyString.contains(knownWeatherWords[wCounter]))
			{
				foundWords.add((knownWeatherWords[wCounter].toLowerCase(Locale.ENGLISH)));
			}
		}
		return foundWords;
	}

	
}
