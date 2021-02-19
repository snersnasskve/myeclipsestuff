package com.sners.snowforecast.data;

import com.sners.snowforecast.data.IntervalData;
import com.sners.snowforecast.data.WeatherHelper;

import java.util.ArrayList;
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

	Set<Integer> weatherCodes;

	////////////////////////////////////////////////////////////////////////////////
	//	Constructor
	////////////////////////////////////////////////////////////////////////////////
	public Daily(JSONArray dailyArray)
	{
		dailyData		= new ArrayList <IntervalData> ();	
		today = null;

		try {

			for (int intervalCounter = 0 ; intervalCounter < dailyArray.length() ; intervalCounter++)
			{
				com.sners.snowforecast.data.DailyData dataInst = new com.sners.snowforecast.data.DailyData(dailyArray.getJSONObject(intervalCounter));
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
		}
	}

	public Long getSunsetTime()
	{
		// Today
		DailyData today = (DailyData) dailyData.get(0);
		Long sunsetTime = Long.parseLong(today.getSunsetTime());
		return sunsetTime;
	}

	public Long getSunriseTime()
	{
		// Today
		DailyData today = (DailyData) dailyData.get(0);
		Long sunriseTime = Long.parseLong(today.getSunriseTime());
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
