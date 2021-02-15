package com.sners.snowforecast.data;

import com.sners.snowforecast.data.IntervalData;

import org.json.JSONException;
import org.json.JSONObject;import com.sners.snowforecast.data.WeatherConstants;



public class DailyData extends IntervalData {

	private String time;
	private String icon;
	private String sunsetTime;
	private String precipIntensityMax;
	private String precipIntensityMaxTime;


	private String windSpeed;
	private String windBearing;


	////////////////////////////////////////////////////////////////////////////////
	//	Constructor
	////////////////////////////////////////////////////////////////////////////////
	public DailyData(JSONObject jsonDaily)
	{
		try {
			time 						= jsonDaily.getString(WeatherConstants.TIME);
			summary 					= jsonDaily.getString(WeatherConstants.SUMMARY);
			sunsetTime 					= jsonDaily.getString(WeatherConstants.SUNSET_TIME);
			precipIntensity 			= jsonDaily.getString(WeatherConstants.PRECIP_INTENSITY);
			precipIntensityMax 			= jsonDaily.getString(WeatherConstants.PRECIP_INTENSITY_MAX);
			precipIntensityMaxTime 		= jsonValueFor		 (WeatherConstants.PRECIP_INTENSITY_MAX_TIME, jsonDaily);
			precipProbability 			= jsonValueFor		 (WeatherConstants.PRECIP_PROBABILITY, 		jsonDaily);
			precipType 					= jsonValueFor		 (WeatherConstants.PRECIP_TYPE, 			jsonDaily);

			windSpeed 					= jsonDaily.getString(WeatherConstants.WIND_SPEED);
			windBearing 				= jsonDaily.getString(WeatherConstants.WIND_BEARING);

		} catch (JSONException e) {
			e.printStackTrace();
		}

	}

		public String getTime() {
		return time;
	}

	public String getIcon() {
		return icon;
	}


	public String
	getSunsetTime() {
		return sunsetTime;
	}


	public String getPrecipIntensityMax() {
		return precipIntensityMax;
	}

	public String getPrecipIntensityMaxTime() {
		return precipIntensityMaxTime;
	}

	public String getWindSpeed() {
		return windSpeed;
	}

	public String getWindBearing() {
		return windBearing;
	}




}
