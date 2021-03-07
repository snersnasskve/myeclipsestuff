package com.sners.snowforecast.data;

import org.json.JSONException;
import org.json.JSONObject;


public class IntervalData {

	protected float precipIntensity;
	protected float precipProbability;
	protected Integer precipType;
	protected String time;
	protected Integer weatherCode;
	protected double windSpeed;	//	Wind speed is metres per second in the data classes
	protected float temperature;


	public IntervalData() {
	}
	
	
	protected String jsonValueFor(String fieldName, JSONObject jsonData)
	{
		String result = null;
		try {
			result = jsonData.getString(fieldName);
		} catch (JSONException e) {
			//e.printStackTrace();
			result = null;
		}
		return result;
	}
	





	////////////////////////////////////////////////////////////////////////////////
	//	Getters
	////////////////////////////////////////////////////////////////////////////////

	public float getPrecipProbability() {
		return precipProbability;
	}

	public float getPrecipIntensity() { return precipIntensity; }

	public Integer getPrecipType() {
		return precipType;
	}

	public String getTime() {
		return time;
	}

	public Integer getWeatherCode() {
		return weatherCode;
	}

	public float getTemperature() {
		return temperature;
	}

	public double getWindSpeed() {
		return windSpeed;
	}
}
