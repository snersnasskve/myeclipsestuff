package com.sners.snowforecast.data;

import com.sners.snowforecast.data.IntervalData;
import com.sners.snowforecast.data.WeatherConstants;
import com.sners.snowforecast.data.WeatherConstants;


import org.json.JSONException;
import org.json.JSONObject;

public class MinutelyData extends IntervalData {


	////////////////////////////////////////////////////////////////////////////////
	//	Constructor
	////////////////////////////////////////////////////////////////////////////////
	public MinutelyData(JSONObject jsonMinutely)
	{
		try {
			time 				= jsonMinutely.getString(WeatherConstants.TIME);
			JSONObject values = jsonMinutely.getJSONObject(WeatherConstants.VALUES);
			precipIntensity 	= values.getString(WeatherConstants.PRECIP_INTENSITY);
			precipProbability 	= values.getString(WeatherConstants.PRECIP_PROBABILITY);
			precipType 			= values.getString(WeatherConstants.PRECIP_TYPE);;
			weatherCode			= values.getInt(WeatherConstants.WEATHER_CODE);;

		} catch (JSONException e) {
			e.printStackTrace();
		}

	}




	
	

}
