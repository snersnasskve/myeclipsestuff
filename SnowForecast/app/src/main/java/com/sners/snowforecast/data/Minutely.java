package com.sners.snowforecast.data;


import com.sners.snowforecast.data.IntervalData;
import com.sners.snowforecast.data.WeatherHelper;
import com.sners.snowforecast.data.WeatherConstants;


import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Minutely {

	private ArrayList <IntervalData> minutelyData;
	
	ArrayList <String> weatherWords;
	
	private Float maxPrecip;

	public Minutely(JSONArray jsonMinutely)
	{
		maxPrecip = -1.0f;
		minutelyData		= new ArrayList <IntervalData> ();	
		try {
			JSONArray  	intervalData	= jsonMinutely;
			for (int intervalCounter = 0 ; intervalCounter < intervalData.length() ; intervalCounter++)
			{
				com.sners.snowforecast.data.MinutelyData dataInst = new com.sners.snowforecast.data.MinutelyData(intervalData.getJSONObject(intervalCounter));
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

	public ArrayList<String> getWeatherWords() {
		return weatherWords;
	}

	public ArrayList<IntervalData> getMinutelyData() {
		return minutelyData;
	}


}
