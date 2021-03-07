package com.sners.snowforecast.data;

import com.sners.snowforecast.data.IntervalData;
import com.sners.snowforecast.data.WeatherHelper;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.sners.snowforecast.data.WeatherConstants;


public class Daily {

	private String summary;
	private String icon;
	private ArrayList <IntervalData> dailyData;
	private com.sners.snowforecast.data.DailyData today;
	LocalDateTime sunriseTime;
	LocalDateTime sunsetTime;

	Set<Integer> weatherCodes = new HashSet<Integer>();
	////////////////////////////////////////////////////////////////////////////////
	//	Constructor
	////////////////////////////////////////////////////////////////////////////////
	public Daily(JSONArray dailyArray)
	{
		dailyData		= new ArrayList <IntervalData> ();	
		today = null;
		WeatherHelper weatherHelper = new WeatherHelper();

		try {

			for (int intervalCounter = 0 ; intervalCounter < dailyArray.length() ; intervalCounter++)
			{
				DailyData dataInst = new DailyData(dailyArray.getJSONObject(intervalCounter));

				dailyData.add(dataInst);
				weatherCodes.add(dataInst.getWeatherCode());
			}



		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (dailyData.size() > 0)
		{
			today 			= (com.sners.snowforecast.data.DailyData) dailyData.get(0);
			//	Set up the times -- only want to parse them once
			DateTimeFormatter formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME;
			TemporalAccessor sunriseParsed = formatter.parse(today.getSunriseTime() );
			sunriseTime = LocalDateTime.from(sunriseParsed);
			TemporalAccessor sunsetParsed = formatter.parse(today.getSunsetTime() );
			sunsetTime = LocalDateTime.from(sunsetParsed);

		}
	}

	public LocalDateTime getSunsetTime()
	{
		return sunsetTime;
	}

	public LocalDateTime getSunriseTime()
	{
		return sunriseTime;
	}
	
	
	public String getSummary() {
		return summary;
	}


	public String getIcon() {
		return icon;
	}

	public ArrayList <IntervalData> getDailyData() {
		return dailyData;
	}

	public Set<Integer> getWeatherCodes() {
		return weatherCodes;
	}

	public DailyData getToday() {
		return today;
	}


	
}
