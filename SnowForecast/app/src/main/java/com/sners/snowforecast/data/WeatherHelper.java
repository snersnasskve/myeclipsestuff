package com.sners.snowforecast.data;

import com.sners.snowforecast.data.IntervalData;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;



public class WeatherHelper {


	//  For displaying temperatures
	public static final DecimalFormat tempFormat = new DecimalFormat("0.0");
	Double beaufortScaleUppers[] = {1.0, 3.0, 7.0, 12.0, 17.0, 24.0, 30.0, 38.0, 46.0, 54.0, 63.0, 73.0};

	String[] knownWeatherWords = {"Snow", "snow", "Rain", "rain", "Drizzle", "drizzle",
			"Flurry", "flurry", "Hail", "hail", "Storm", "storm"};

	private static HashMap<Integer, String> weatherCodes = new HashMap<Integer, String>();
	private static HashMap<Integer, String> iconCodes = new HashMap<Integer, String>();
	private static HashMap<Integer, String> precipTypes = new HashMap<Integer, String>();



	////////////////////////////////////////////////////////////////////////////////
	//	Constructor
	////////////////////////////////////////////////////////////////////////////////
	public WeatherHelper() {
		// Populate weathercodes
		populateWeatherCodes();
	}

	////////////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////
	public int periodWhenValueExceededPrecipIntensity(ArrayList<IntervalData> intervalData, double minValue)
	{
		int periodFound = -1;
		boolean precipExceedsMin = false;
	
			int intervalCounter = 0;
			while (intervalCounter < intervalData.size() && !precipExceedsMin)
			{
				Float 		fieldValue = intervalData.get(intervalCounter).getPrecipIntensity();
				if (fieldValue > minValue)
				{
					precipExceedsMin = true;
					periodFound = intervalCounter;
				}
				intervalCounter++;
			}
		return periodFound;
	}

	////////////////////////////////////////////////////////////////////////////////
	public int windSpeedToBeaufort(Double windSpeed)
	{
		//	Wind speed from clima cell is meters per second

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

	////////////////////////////////////////////////////////////////////////////////
	public String precipIntensityToMilsFormatted(String inchesString)
	{
		Float mils = precipIntensityToMils(inchesString);
		return tempFormat.format(mils) + WeatherConstants.MM_HR;
	}

	////////////////////////////////////////////////////////////////////////////////
	public Float precipIntensityToMils(String inchesString)
	{
		Float inches = Float.parseFloat(inchesString);
		return (inches * 2.54f);
	}


	////////////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////
	public String probabilityToPercent(String decimalNo)
	{
		Double dec = Double.parseDouble(decimalNo);
		Double perc = (dec * 100);
		return tempFormat.format(perc) + WeatherConstants.PERCENT;
	}

	////////////////////////////////////////////////////////////////////////////////
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


	////////////////////////////////////////////////////////////////////////////////
	//	PrecipitationTypeForCode - Meansings are direct from the API documentation
	//	enum is not the answer - the API gives you numbers - Java enums are not numbes
	////////////////////////////////////////////////////////////////////////////////
	public String precipitationTypeForCode(int weatherCode) {

		String precipType = "Unknown";
		if (precipTypes.containsKey(weatherCode)) {
			precipType = precipTypes.get(weatherCode);
		}
		return precipType;
	}

	public int codeForPrecipitationType(String weatherDesc) {
		int weatherCode = -1; //
		for (int key : precipTypes.keySet()) {
			if (precipTypes.get(key).equals(weatherDesc)) {
				weatherCode = key;
			}
		}
		return weatherCode;
	}

	public int codeForWeatherWord(String weatherWord) {
		int weatherCode = -1;
		for (int key : weatherCodes.keySet()) {
			if (weatherCodes.get(key).equals(weatherWord)) {
				weatherCode = key;
			}
		}
		return weatherCode;
	}

	public boolean isWeatherCodeSnowType(Integer weatherCode) {
		return (weatherCode >= 5000 && weatherCode < 8000);
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
		iconCodes.put(1000, "clear_day");
		iconCodes.put(1001, "Cloudy");
		iconCodes.put(1100, "clear_day");
		iconCodes.put(1101, "partly_cloudy_day");
		iconCodes.put(1102, "partly_cloudy_day");
		iconCodes.put(2000, "fog");
		iconCodes.put(2100, "fog");
		iconCodes.put(3000, "wind");
		iconCodes.put(3001, "wind");
		iconCodes.put(3002, "wind");
		iconCodes.put(4000, "rain");
		iconCodes.put(4001, "rain");
		iconCodes.put(4200, "rain");
		iconCodes.put(4201, "rain");
		iconCodes.put(5000, "snow");
		iconCodes.put(5001, "snow");
		iconCodes.put(5100, "heavy_snow");
		iconCodes.put(5101, "heavy_snow");
		iconCodes.put(6000, "sleet");
		iconCodes.put(6001, "sleet");
		iconCodes.put(6200, "sleet");
		iconCodes.put(6201, "sleet");
		iconCodes.put(7000, "sleet");
		iconCodes.put(7101, "sleet");
		iconCodes.put(7102, "sleet");
		iconCodes.put(8000, "rain");

		precipTypes.put(0, "N/A");
		precipTypes.put(1, WeatherConstants.PRECIP_TYPE_RAIN);
		precipTypes.put(2, WeatherConstants.PRECIP_TYPE_SNOW);
		precipTypes.put(3, WeatherConstants.PRECIP_TYPE_FREEZING_RAIN);
		precipTypes.put(4, WeatherConstants.PRECIP_TYPE_ICE_PELLETS);
		precipTypes.put(5, WeatherConstants.PRECIP_TYPE_OTHER);

	}
}
