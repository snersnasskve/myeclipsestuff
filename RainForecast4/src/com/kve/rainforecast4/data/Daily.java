package com.kve.rainforecast4.data;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Daily {

	private String summary;
	private String icon;
	private ArrayList <IntervalData> dailyData;
	private DailyData today;

	private ArrayList <String> weatherWords;

	public Daily(JSONObject jsonDaily)
	{
		dailyData		= new ArrayList <IntervalData> ();	
		today = null;

		try {
			summary 			= jsonDaily.getString("summary");
			icon 				= jsonDaily.getString("icon");

			JSONArray  	intervalData	= jsonDaily.getJSONArray("data");
			for (int intervalCounter = 0 ; intervalCounter < intervalData.length() ; intervalCounter++)
			{
				DailyData dataInst = new DailyData(intervalData.getJSONObject(intervalCounter));
				dailyData.add(dataInst);
			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (dailyData.size() > 0)
		{
			today 			= (DailyData) dailyData.get(0);
		}
		
		WeatherHelper weatherHelper = new WeatherHelper();
		weatherWords = weatherHelper.weatherWordsFromString(jsonDaily.toString());
	}

	public Long getSunsetTime()
	{
		// Today
		DailyData today = (DailyData) dailyData.get(0);
		Long sunsetTime = Long.parseLong(today.getSunsetTime());
		return sunsetTime;
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

	public ArrayList<String> getWeatherWords() {
		return weatherWords;
	}

	public DailyData getToday() {
		return today;
	}


	
}
