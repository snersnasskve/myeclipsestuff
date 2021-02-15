package com.sners.snowforecast.data;

import org.json.JSONException;
import org.json.JSONObject;


public class IntervalData {

	protected String summary;
	protected float precipIntensity;
	protected float precipProbability;
	protected Integer precipType;
	protected String time;
	protected Integer weatherCode;

	protected WeatherHelper weatherHelper;
	public IntervalData() {
		weatherHelper = new WeatherHelper();
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

	public String getSummary() {
		return summary;
	}

}
