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

	////////////////////////////////////////////////////////////////////////////////
	//	Constructor
	////////////////////////////////////////////////////////////////////////////////
	public Minutely(JSONArray minutelyArray)
	{
		maxPrecip = -1.0f;
		minutelyData		= new ArrayList <IntervalData> ();	
		try {
			for (int intervalCounter = 0 ; intervalCounter < minutelyArray.length() ; intervalCounter++)
			{
				JSONObject minuteObj = minutelyArray.getJSONObject(intervalCounter);
				com.sners.snowforecast.data.MinutelyData dataInst =
						new com.sners.snowforecast.data.MinutelyData(minuteObj);
				minutelyData.add(dataInst);
			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		WeatherHelper weatherHelper = new WeatherHelper();
		weatherWords = weatherHelper.weatherWordsFromString(minutelyArray.toString());

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
