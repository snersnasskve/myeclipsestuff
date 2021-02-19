package com.sners.snowforecast.data;

import com.sners.snowforecast.data.IntervalData;
import com.sners.snowforecast.data.WeatherHelper;

import java.util.ArrayList;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;import com.sners.snowforecast.data.WeatherConstants;



public class Hourly {

	String summary;
	String icon;
	ArrayList <IntervalData> hourlyData;
	
	private Float maxPrecip;

	Set<Integer> weatherCodes;
	Set<Integer> precipCodes;


	////////////////////////////////////////////////////////////////////////////////
	//	Constructor
	////////////////////////////////////////////////////////////////////////////////
	public Hourly(JSONArray hourlyArary)
	{
		maxPrecip = -1.0f;
		hourlyData		= new ArrayList <IntervalData> ();	
		try {
			//summary 			= jsonHourly.getString(WeatherConstants.SUMMARY);
			//icon 				= jsonHourly.getString(WeatherConstants.ICON);
			summary = "Please implement me";
			icon = "Please implement me";

			for (int intervalCounter = 0 ; intervalCounter < hourlyArary.length() ; intervalCounter++)
			{
				com.sners.snowforecast.data.HourlyData dataInst = new com.sners.snowforecast.data.HourlyData(hourlyArary.getJSONObject(intervalCounter));
				hourlyData.add(dataInst);
				weatherCodes.add(dataInst.getWeatherCode());
				precipCodes.add(dataInst.getPrecipType());
			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}


	public Float getMaxPrecip()
	{
		if (maxPrecip < 0.0f)
		{
			//	only calculate it once
			for (IntervalData hour : hourlyData)
			{
				Float precip = hour.getPrecipIntensity();
				if (precip > maxPrecip)
				{
					maxPrecip = precip;
				}
			}
		}
		return maxPrecip;
	}

	public String getSummary() {
		return summary;
	}


	public String getIcon() {
		return icon;
	}

	public Set<Integer> getWeatherCodes() {
		return weatherCodes;
	}

	public Set<Integer> getPrecipCodes() {
		return precipCodes;
	}

	public ArrayList<IntervalData> getHourlyData() {
		return hourlyData;
	}



}
