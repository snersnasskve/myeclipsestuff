package  com.sners.snowforecast.data;

import java.time.Period;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
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
 *

 *
 */

import android.util.Log;

import com.sners.snowforecast.data.Alert;
import com.sners.snowforecast.data.AlertData;
import com.sners.snowforecast.data.CurrentlyData;
import com.sners.snowforecast.data.Daily;
import com.sners.snowforecast.data.Hourly;
import com.sners.snowforecast.data.IntervalData;
import com.sners.snowforecast.data.Minutely;
import com.sners.snowforecast.data.WeatherHelper;

public class WeatherData {


	private Currently currently;
	private Minutely minutely;
	private Hourly hourly;
	private Daily daily;
	private Alert alert;
	
	private WeatherHelper weatherHelper;
	
	private String 			headlineSummary;
	private String			headlineIcon;
	private long 			minutesTilSunset;

	enum TimePeriod {
		MINUTELY,
		HOURLY,
		DAILY,
		UNKNOWN
	};

	////////////////////////////////////////////////////////////////////////////////
	//	Constructor
	////////////////////////////////////////////////////////////////////////////////
	public WeatherData(String jsonData)
	{
		weatherHelper = new WeatherHelper();
		
		JSONObject jsonObj;
		try {
			jsonObj = new JSONObject(jsonData);

			JSONObject tempObj = 	jsonObj.getJSONObject(WeatherConstants.DATA);
			JSONArray timelinesArray = 	tempObj.getJSONArray(WeatherConstants.TIMELINE);

			//	Set up the main data objects
			for (int timelineCounter = 0 ; timelineCounter < timelinesArray.length() ; timelineCounter++) {
				JSONObject timelineObj = timelinesArray.getJSONObject(timelineCounter);
				switch (getPeriodForTimestep( timelineObj.getString(WeatherConstants.TIMESTEP))) {
					case MINUTELY:
						minutely = new Minutely(timelineObj.getJSONArray(WeatherConstants.INTERVALS));
						currently = new Currently(minutely.getMinutelyData());
						break;
					case HOURLY:
						hourly = new Hourly(timelineObj.getJSONArray(WeatherConstants.INTERVALS));
						break;
					case DAILY:
						daily = new Daily(timelineObj.getJSONArray(WeatherConstants.INTERVALS));
						break;
					case UNKNOWN:
						break;
					default: {

						break;
					}
				}
			}

			headlineSummary = currently.getSummary();
			headlineIcon	= currently.getIcon();

			
			minutesTilSunset = -1;
			

		} catch (JSONException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		
	}

	public String getHeadlineSummary()
	{
		return headlineSummary;
	}

	public String getHeadlineIcon()
	{
		return headlineIcon;
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////
	//	Daily stuff  --  Can't go into daily as need to compare against currently
	////////////////////////////////////////////////////////////////////////////////////////////////////////
	public Long getTimeTilSunset()
	{
		//	If it's night this will be negative
		if (minutesTilSunset < 0)
		{
			minutesTilSunset = 0;
			//	Calculate it
			
			Long timeNowUnix = Long.parseLong(currently.getTime());
			Long sunsetUnix  = daily.getSunsetTime();
			
			if (sunsetUnix > timeNowUnix)
			{
				minutesTilSunset = (sunsetUnix - timeNowUnix) / 60;
			}
		}
		
		return minutesTilSunset;
	}
	
	public String getTimeTilSunsetString()
	{
		String timeTilSunsetString = weatherHelper.formatTime(getTimeTilSunset());
		
		return timeTilSunsetString;
	}

	public boolean isDayTime()
	{
		boolean dayTime = true;
		Long 		timeNowUnix = Long.parseLong(currently.getTime());
		Long 		sunriseUnix	= Long.parseLong(daily.getToday().getSunriseTime());
		Long 		sunsetUnix	= Long.parseLong(daily.getToday().getSunsetTime());

		if (timeNowUnix < sunriseUnix || timeNowUnix > sunsetUnix)
		{
			dayTime = false;
		}

		return dayTime;
	}

	
	
	
////////////////////////////////////////////////////////////////////////////////////////////////////////
//	Daily stuff  --  Can't go into daily as need to compare against currently
////////////////////////////////////////////////////////////////////////////////////////////////////////

	
	//precipIntensity: A numerical value representing the average expected intensity (in inches of liquid water per hour) 
	//	of precipitation occurring at the given time conditional on probability (that is, assuming any precipitation occurs at all). 
	//	A very rough guide is that a value of 0 in./hr. corresponds to no precipitation, 
	//	0.002 in./hr. corresponds to very light precipitation, 
	//	0.017 in./hr. corresponds to light precipitation, 
	//	0.1 in./hr. corresponds to moderate precipitation, 
	//	and 0.4 in./hr. corresponds to heavy precipitation.
	public long timeTilPrecip(boolean toIgnoreLightPrecip)
	{
		long minutesTilPrecip = -1;
		
		Float minPrecip = 0.002f;
		if (toIgnoreLightPrecip){
			minPrecip = 0.017f;
		}

			Float precipIntensity 	= currently.getPrecipIntensityNum();
			if (precipIntensity < minPrecip)
			{
				//	Check minutely
				if (null != minutely)
				{
					int rainMinutes = weatherHelper.periodWhenValueExceededPrecipIntensity(minutely.getMinutelyData(), minPrecip);
					if (rainMinutes >= 0)
					{
						minutesTilPrecip = (rainMinutes + 1);
					}
				}
				if (minutesTilPrecip == 0)
				{
					//	Check hourly
					if (null != hourly)
					{
						int rainHours = weatherHelper.periodWhenValueExceededPrecipIntensity(hourly.getHourlyData(), minPrecip);
						if (rainHours >= 0)
						{
							minutesTilPrecip = ((rainHours + 1) * 60);
						}
					}	
				}
				if (minutesTilPrecip == 0)
				{
					//	Check hourly
					if (null != daily)
					{
						int rainDays = weatherHelper.periodWhenValueExceededPrecipIntensity(daily.getDailyData(), minPrecip);
						if (rainDays >= 0)
						{
							minutesTilPrecip = ((rainDays + 1) * 60 * 24);
						}
						else
						{
							minutesTilPrecip = -1;
						}
					}	
				}
			}

	
		return minutesTilPrecip;
	}
	
	public String timeTilPrecipString(boolean toIgnoreLightPrecip)
	{
		String timeTilString = WeatherConstants.NONE_FORECAST;
		long timeTil = timeTilPrecip(toIgnoreLightPrecip);
		if (timeTil > 0)
		{
			timeTilString = weatherHelper.formatTime(timeTil);
		}
		else if (timeTil == 0)
		{
			timeTilString = WeatherConstants.NOW;
		}

		return timeTilString;
	}

	
	//	tiemKeyWord: "daily", "hourly", "minutely", "currently"
	public boolean dataContainsWeatherword(String weatherWord, String timeKeyWord)
	{
		boolean wordFound = false;
		ArrayList<String> validWords =
				new ArrayList<String>(Arrays.asList(WeatherConstants.CURRENTLY, WeatherConstants.MINUTELY,
						WeatherConstants.HOURLY, WeatherConstants.DAILY));
		if (validWords.contains(timeKeyWord)){
			int keywordPos = validWords.indexOf(timeKeyWord);
			
			if (0 == keywordPos && null != currently)
			{
				if (currently.getSummary().toLowerCase(Locale.ENGLISH).contains(weatherWord) )
				{
					wordFound = true;
				}
			}

			else if (2 >= keywordPos && null != hourly)
			{
				if (hourly.getSummary().toLowerCase(Locale.ENGLISH).contains(weatherWord)||
						 (hourly.getWeatherWords().contains(weatherWord)))
				{
					wordFound = true;
				}
			
			}
			else if (3 >= keywordPos && null != daily)
			{
				if (daily.getSummary().toLowerCase(Locale.ENGLISH).contains(weatherWord)||
						 (daily.getWeatherWords().contains(weatherWord)))
				{
					wordFound = true;
				}
			
			}

		}
		return wordFound;
	}

	public String timeTilPrecipTypeString(String precipType)
	{
		long timeTil = -1;
		
		if (null != currently)
		{
			if (currently.getSummary().toLowerCase(Locale.ENGLISH).contains(precipType))
			{
				timeTil = 0;
			}
		}
		
		//	Minutely data doesn't come with a weather word so we have to guess that if the summary contains the word - then the precip is of type
		if (0 > timeTil && null != minutely && (minutely.getWeatherWords().contains(precipType.toLowerCase(Locale.ENGLISH))))
		{
			int precipMinute = weatherHelper.intervalCounterForKeyword(minutely.getMinutelyData(), precipType);
		if (0 <= precipMinute)
			{
				timeTil = (precipMinute * 60);
			}
		}

		if (0 > timeTil && null != hourly && (hourly.getWeatherWords().contains(precipType.toLowerCase(Locale.ENGLISH))))
		{
			int precipMinute = weatherHelper.intervalCounterForKeyword(hourly.getHourlyData(), precipType);
			if (0 <= precipMinute)
			{
				timeTil = (precipMinute * 60);
			}
		}

		if (0 > timeTil && null != daily && (daily.getWeatherWords().contains(precipType.toLowerCase(Locale.ENGLISH))))
		{
			int precipMinute = weatherHelper.intervalCounterForKeyword(daily.getDailyData(), precipType);
			if (0 <= precipMinute)
			{
				timeTil = (precipMinute * 60 * 60);
			}
		}
			
		
		String timeTilString = WeatherConstants.NONE_FORECAST;
		
		if (timeTil > 0)
		{
			timeTilString = weatherHelper.formatTime(timeTil);
		}
		else if (timeTil == 0)
		{
			timeTilString = WeatherConstants.NOW;
		}
		return timeTilString;
	}


	//	Getters
	public Currently getCurrently()
	{
		return currently;
	}
	
	public ArrayList <IntervalData> getMinutelyData()
	{
		if (null == minutely)
		{
			return null;
		}
		else
		{
			return minutely.getMinutelyData();
		}
	}


	public Minutely getMinutely()
	{
		return minutely;
	}

	public ArrayList <IntervalData> getHourlyData()
	{
		if (null == hourly)
		{
			return null;
		}
		else
		{
			return hourly.getHourlyData();
		}
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////
	private TimePeriod getPeriodForTimestep(String timestep) {
		if (timestep == "1s") {
			return TimePeriod.MINUTELY;
		}
		else if (timestep == "1h") {
			return TimePeriod.HOURLY;
		}
		else if (timestep == "1d") {
			return TimePeriod.DAILY;
		}
		else {
			return TimePeriod.UNKNOWN;
		}
	}

	public Hourly getHourly()
	{
		return hourly;
	}

	public Alert getAlerts()
	{
		return alert;
	}

	public ArrayList <AlertData> getAlertsData()
	{
		return alert.getAlertData();
	}


}
