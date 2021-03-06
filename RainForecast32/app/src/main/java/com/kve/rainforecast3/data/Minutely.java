package com.kve.rainforecast3.data;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Minutely {

	private String summary;
	private String icon;
	private ArrayList <IntervalData> minutelyData;
	
	ArrayList <String> weatherWords;
	
	private Float maxPrecip;

	public Minutely(JSONObject jsonMinutely)
	{
		maxPrecip = -1.0f;
		minutelyData		= new ArrayList <IntervalData> ();	
		try {
			summary 			= jsonMinutely.getString("summary");
			icon 				= jsonMinutely.getString("icon");

			JSONArray  	intervalData	= jsonMinutely.getJSONArray("data");
			for (int intervalCounter = 0 ; intervalCounter < intervalData.length() ; intervalCounter++)
			{
				MinutelyData dataInst = new MinutelyData(intervalData.getJSONObject(intervalCounter));
				minutelyData.add(dataInst);
			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		WeatherHelper weatherHelper = new WeatherHelper();
		weatherWords = weatherHelper.weatherWordsFromString(jsonMinutely.toString());

	}

	public Float getMaxPrecip()
	{
		if (maxPrecip < 0.0f)
		{
			//	only calculate it once
			for (IntervalData minute : minutelyData)
			{
				Float precip = minute.getPrecipIntensityNum();
				if (precip > maxPrecip)
				{
					maxPrecip = precip;
				}
			}
		}
		return maxPrecip;
	}

	public String getSummary() {
		return summary;
	}


	public String getIcon() {
		return icon;
	}

	public ArrayList<String> getWeatherWords() {
		return weatherWords;
	}

	public ArrayList<IntervalData> getMinutelyData() {
		return minutelyData;
	}


}
