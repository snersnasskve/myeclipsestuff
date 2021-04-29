package com.sners.snowforecast.data;

import com.sners.snowforecast.data.IntervalData;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;



public class WeatherHelper {


	//  For displaying temperatures

	String[] knownWeatherWords = {"Snow", "snow", "Rain", "rain", "Drizzle", "drizzle",
			"Flurry", "flurry", "Hail", "hail", "Storm", "storm"};

	private static HashMap<Integer, String> weatherCodes = new HashMap<Integer, String>();
	private static HashMap<Integer, String> iconCodes = new HashMap<Integer, String>();
	private static HashMap<Integer, String> precipTypes = new HashMap<Integer, String>();

	////////////////////////////////////////////////////////////////////////////////



	
	////////////////////////////////////////////////////////////////////////////////
	//	Format Strings
	////////////////////////////////////////////////////////////////////////////////

	public Double fahrenheitToCelsius(String fahrenheit)
	{
		Double fahr = Double.parseDouble(fahrenheit);
		Double cels = (fahr - 32)*5/9;
		return cels ;
	}

	////////////////////////////////////////////////////////////////////////////////
	public String precipIntensityToMilsFormatted(String inchesString)
	{
		Float mils = precipIntensityToMils(inchesString);
		return String.format("%.1g%s", mils, WeatherConstants.MM_HR);
	}

	////////////////////////////////////////////////////////////////////////////////
	public Float precipIntensityToMils(String inchesString)
	{
		Float inches = Float.parseFloat(inchesString);
		return (inches * 2.54f);
	}


	////////////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////
	public String probabilityToPercent(Float prob)
	{
		Double perc = (prob * 100.0);
		return String.format("%.1f %s",perc, WeatherConstants.PERCENT);
	}

	////////////////////////////////////////////////////////////////////////////////
	public String formatTime(long totalMinutes)
	{
		long hours = totalMinutes / 60;
		long mins = totalMinutes % 60;
		if (hours > 24) {
			long days = hours / 24;
			hours = hours % 24;
			return String.format("%d days, %2d:%02d", days, hours, mins);
		}
		else {
			return String.format("%d:%02d", hours, mins);
		}

	}

	////////////////////////////////////////////////////////////////////////////////
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

	public String iconForWeatherCode(Integer weatherCode) {

		String weatherWord = "clear_day";
		if (iconCodes.containsKey(weatherCode)) {
			weatherWord = iconCodes.get(weatherCode);
		}
		return weatherWord;
	}

}
