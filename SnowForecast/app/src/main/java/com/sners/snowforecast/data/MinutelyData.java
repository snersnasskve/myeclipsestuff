package com.sners.snowforecast.data;

import com.sners.snowforecast.data.IntervalData;
import com.sners.snowforecast.data.WeatherConstants;
import com.sners.snowforecast.data.WeatherConstants;


import org.json.JSONException;
import org.json.JSONObject;

public class MinutelyData extends IntervalData {

	protected float temperature;


	////////////////////////////////////////////////////////////////////////////////
	//	Constructor
	////////////////////////////////////////////////////////////////////////////////
	public MinutelyData(JSONObject jsonMinutely)
	{
		try {
			time 				= jsonMinutely.getString(WeatherConstants.TIME);
			JSONObject values = jsonMinutely.getJSONObject(WeatherConstants.VALUES);
			precipIntensity 	= (float)values.getDouble(WeatherConstants.PRECIP_INTENSITY);
			precipProbability 	= (float)values.getDouble(WeatherConstants.PRECIP_PROBABILITY);
			precipType 			= values.getInt(WeatherConstants.PRECIP_TYPE);;
			weatherCode			= values.getInt(WeatherConstants.WEATHER_CODE);
			temperature			= (float) values.getDouble(WeatherConstants.TEMPERATURE);
			windSpeed			= (float) values.getDouble(WeatherConstants.WIND_SPEED);

		} catch (JSONException e) {
			e.printStackTrace();
		}

	}


	public float getTemperature() {
		return temperature;
	}

	public void setTemperature(float temperature) {
		this.temperature = temperature;
	}
}
