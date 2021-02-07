package com.sners.snowforecast.data;

import com.sners.snowforecast.data.IntervalData;
import com.sners.snowforecast.data.WeatherConstants;
import com.sners.snowforecast.data.WeatherConstants;


import org.json.JSONException;
import org.json.JSONObject;

public class MinutelyData extends IntervalData {

	String time;
	
	public MinutelyData(JSONObject jsonMinutely)
	{
		try {
			time 				= jsonMinutely.getString(WeatherConstants.TIME);
			precipIntensity 	= jsonMinutely.getString(WeatherConstants.PRECIP_INTENSITY);
			precipProbability 	= jsonMinutely.getString(WeatherConstants.PRECIP_PROBABILITY);
			precipType 			= jsonValueFor		  (WeatherConstants.PRECIP_TYPE, jsonMinutely);
	
		} catch (JSONException e) {
			e.printStackTrace();
		}

	}

	public String getTime() {
		return time;
	}


	
	

}
