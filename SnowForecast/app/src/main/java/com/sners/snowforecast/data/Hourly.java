package com.sners.snowforecast.data;

import com.sners.snowforecast.data.IntervalData;
import com.sners.snowforecast.data.WeatherHelper;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;import com.sners.snowforecast.data.WeatherConstants;



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
			summary 			= jsonHourly.getString(WeatherConstants.SUMMARY);
			icon 				= jsonHourly.getString(WeatherConstants.ICON);

			JSONArray  	intervalData	= jsonHourly.getJSONArray(WeatherConstants.DATA);
			for (int intervalCounter = 0 ; intervalCounter < intervalData.length() ; intervalCounter++)
			{
				com.sners.snowforecast.data.HourlyData dataInst = new com.sners.snowforecast.data.HourlyData(intervalData.getJSONObject(intervalCounter));
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
