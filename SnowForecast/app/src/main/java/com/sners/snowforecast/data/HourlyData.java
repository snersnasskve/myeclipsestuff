package com.sners.snowforecast.data;

import com.sners.snowforecast.data.IntervalData;

import org.json.JSONException;
import org.json.JSONObject;
import com.sners.snowforecast.data.WeatherConstants;


public class HourlyData extends IntervalData {

	private String time;
	private String icon;
	private String pressure;

	////////////////////////////////////////////////////////////////////////////////
	//	Constructor
	////////////////////////////////////////////////////////////////////////////////
	public HourlyData(JSONObject jsonHourly)
	{
		try {
			time				= jsonHourly.getString(WeatherConstants.TIME);
			JSONObject values = jsonHourly.getJSONObject(WeatherConstants.VALUES);
			precipIntensity 	= (float)values.getDouble(WeatherConstants.PRECIP_INTENSITY);
			precipProbability 	= (float)values.getDouble(WeatherConstants.PRECIP_PROBABILITY);
			precipType 			= values.getInt(WeatherConstants.PRECIP_TYPE);;
			temperature 		= (float) values.getDouble(WeatherConstants.TEMPERATURE);
			windSpeed 			= values.getDouble(WeatherConstants.WIND_SPEED);
			weatherCode			= values.getInt(WeatherConstants.WEATHER_CODE);


		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

		
	public String getTime() {
		return time;
	}


		public float getTemperature() {
		return temperature;
	}




}
