package com.sners.snowforecast.data;

import com.sners.snowforecast.data.IntervalData;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;



public class WeatherHelper {

	public static final String MM_HR = " mm/hr";
	public static final String PERCENT = " %";

	//  For displaying temperatures
	public static final DecimalFormat tempFormat = new DecimalFormat("0.0");
	Double beaufortScaleUppers[] = {1.0, 3.0, 7.0, 12.0, 17.0, 24.0, 30.0, 38.0, 46.0, 54.0, 63.0, 73.0};

	String[] knownWeatherWords = {"Snow", "snow", "Rain", "rain", "Drizzle", "drizzle",
			"Flurry", "flurry", "Hail", "hail", "Storm", "storm"};

	private static HashMap<Integer, String> weatherCodes = new HashMap<Integer, String>();



	////////////////////////////////////////////////////////////////////////////////
	//	Constructor
	////////////////////////////////////////////////////////////////////////////////
	public WeatherHelper() {
		// Populate weathercodes
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

	public ArrayList <String> weatherWordsFromString(String anyString) {
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

	////////////////////////////////////////////////////////////////////////////////
	//	SummaryForWeatherCode
	////////////////////////////////////////////////////////////////////////////////
	public String summaryForWeatherCode(Integer weatherCode) {

		String weatherWord = "Unidentified Weather";
		if (weatherCodes.containsKey(weatherCode)) {
			weatherWord = weatherCodes.get(weatherCode);
		}
		return weatherWord;
	}

	////////////////////////////////////////////////////////////////////////////////
	//	PrecipitationTypeForCode - Meansings are direct from the API documentation
	////////////////////////////////////////////////////////////////////////////////
	public String precipitationTypeForCode(Integer weatherCode) {

		String precipType = "Unknown";
		switch (weatherCode) {
			case 0:
				precipType = "N/A";
				break;
			case 1:
				precipType = "Rain";
				break;
			case 2:
				precipType = "Snow";
				break;
			case 3:
				precipType = "Freezing Rain";
				break;
			case 4:
				precipType = "Ice Pellets";
				break;
			default:
				precipType = "New precipitation type";
		}
		return precipType;
	}

	////////////////////////////////////////////////////////////////////////////////
	//	Populate weather codes
	//	https://docs.climacell.co/reference/data-layers-core#data-layers-weather-codes
	////////////////////////////////////////////////////////////////////////////////
	private void populateWeatherCodes() {
		
		//	This is a fixed array per API - only used once so not using constants
		weatherCodes.put(1000, "Clear");
		weatherCodes.put(1001, "Cloudy");
		weatherCodes.put(1100, "Mostly Clear");
		weatherCodes.put(1101, "Partly Cloudy");
		weatherCodes.put(1102, "Mostly Cloudy");
		weatherCodes.put(2000, "Fog");
		weatherCodes.put(2100, "Light Fog");
		weatherCodes.put(3000, "Light Wind");
		weatherCodes.put(3001, "Wind");
		weatherCodes.put(3002, "Strong Wind");
		weatherCodes.put(4000, "Drizzle");
		weatherCodes.put(4001, "Rain");
		weatherCodes.put(4200, "Light Rain");
		weatherCodes.put(4201, "Heavy Rain");
		weatherCodes.put(5000, "Snow");
		weatherCodes.put(5001, "Flurries");
		weatherCodes.put(5100, "Light Snow");
		weatherCodes.put(5101, "Heavy Snow");
		weatherCodes.put(6000, "Freezing Drizzle");
		weatherCodes.put(6001, "Freezing Rain");
		weatherCodes.put(6200, "Light Freezing Rain");
		weatherCodes.put(6201, "Heavy Freezing Rain");
		weatherCodes.put(7000, "Ice Pellets");
		weatherCodes.put(7101, "Heavy Ice Pellets");
		weatherCodes.put(7102, "Light Ice Pellets");
		weatherCodes.put(8000, "Thunderstorm");
	}
}
