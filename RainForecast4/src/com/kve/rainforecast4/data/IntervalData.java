package com.kve.rainforecast4.data;

import org.json.JSONException;
import org.json.JSONObject;


public class IntervalData {

	protected String summary;
	protected String precipIntensity;
	protected String precipProbability;
	protected String precipType;

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
	
	public String getSummary() {
		return summary;
	}
	public String getPrecipIntensity() {
		String formattedPrecip = weatherHelper.precipIntensityToMilsFormatted(precipIntensity);
		return formattedPrecip;
	}

	public Float getPrecipIntensityNum() {
		Float precipIntensityNum = weatherHelper.precipIntensityToMils(precipIntensity);
		return precipIntensityNum;
	}

	public String getPrecipProbability() {
		return precipProbability;
	}

	public String getPrecipType() {
		return precipType;
	}

}
