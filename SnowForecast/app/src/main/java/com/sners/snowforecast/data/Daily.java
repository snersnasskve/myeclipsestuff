package com.sners.snowforecast.data;

import com.sners.snowforecast.data.IntervalData;
import com.sners.snowforecast.data.WeatherHelper;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.sners.snowforecast.data.WeatherConstants;


public class Daily {

	private String summary;
	private String icon;
	private ArrayList <IntervalData> dailyData;
	private com.sners.snowforecast.data.DailyData today;

	private ArrayList <String> weatherWords;

	////////////////////////////////////////////////////////////////////////////////
	//	Constructor
	////////////////////////////////////////////////////////////////////////////////
	public Daily(JSONArray dailyArray)
	{
		dailyData		= new ArrayList <IntervalData> ();	
		today = null;

		try {
			//summary 			= jsonDaily.getString(WeatherConstants.SUMMARY);
			//icon 				= jsonDaily.getString(WeatherConstants.ICON);

			for (int intervalCounter = 0 ; intervalCounter < dailyArray.length() ; intervalCounter++)
			{
				com.sners.snowforecast.data.DailyData dataInst = new com.sners.snowforecast.data.DailyData(dailyArray.getJSONObject(intervalCounter));
				dailyData.add(dataInst);
			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (dailyData.size() > 0)
		{
			today 			= (com.sners.snowforecast.data.DailyData) dailyData.get(0);
		}
		
		WeatherHelper weatherHelper = new WeatherHelper();
		weatherWords = weatherHelper.weatherWordsFromString(dailyArray.toString());
	}

	public Long getSunsetTime()
	{
		// Today
		com.sners.snowforecast.data.DailyData today = (com.sners.snowforecast.data.DailyData) dailyData.get(0);
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

	public com.sners.snowforecast.data.DailyData getToday() {
		return today;
	}


	
}
