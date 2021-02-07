package com.kve.rainforecast3.data;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Hourly {

	String summary;
	String icon;
	ArrayList <IntervalData> hourlyData;
	
	private Float maxPrecip;

	private ArrayList <String> weatherWords;

	public Hourly(JSONObject jsonHourly)
	{
		maxPrecip = -1.0f;
		hourlyData		= new ArrayList <IntervalData> ();	
		try {
			summary 			= jsonHourly.getString("summary");
			icon 				= jsonHourly.getString("icon");

			JSONArray  	intervalData	= jsonHourly.getJSONArray("data");
			for (int intervalCounter = 0 ; intervalCounter < intervalData.length() ; intervalCounter++)
			{
				HourlyData dataInst = new HourlyData(intervalData.getJSONObject(intervalCounter));
				hourlyData.add(dataInst);
			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		WeatherHelper weatherHelper = new WeatherHelper();
		weatherWords = weatherHelper.weatherWordsFromString(jsonHourly.toString());

	}


	public Float getMaxPrecip()
	{
		if (maxPrecip < 0.0f)
		{
			//	only calculate it once
			for (IntervalData hour : hourlyData)
			{
				Float precip = hour.getPrecipIntensityNum();
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


	public ArrayList<IntervalData> getHourlyData() {
		return hourlyData;
	}



}
