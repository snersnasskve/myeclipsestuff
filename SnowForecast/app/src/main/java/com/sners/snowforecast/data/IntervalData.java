package com.sners.snowforecast.data;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class IntervalData {

	protected float precipIntensity;
	protected float precipProbability;
	protected String time;
	protected double windSpeed;	//	Wind speed is metres per second in the data classes
	protected float temperature;
	protected float tempFeelsLike;
	protected ArrayList<String> weatherWords = new  ArrayList<String>();


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
		return (precipProbability / 100.0f);
	}

	public float getPrecipIntensity() { return precipIntensity; }

	public String getTime() {
		return time;
	}


	public float getTemperature() {
		return temperature;
	}

	public double getWindSpeed() {
		return windSpeed;
	}

	public ArrayList<String> getWeatherWords() {
		return weatherWords;
	}

	public float getTempFeelsLike() {
		return tempFeelsLike;
	}
}
