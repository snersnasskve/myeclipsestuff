package com.sners.snowforecast.data;

import com.sners.snowforecast.data.IntervalData;

import org.json.JSONException;
import org.json.JSONObject;import com.sners.snowforecast.data.WeatherConstants;



public class DailyData extends IntervalData {

	private String time;
	private String icon;
	private String sunsetTime;
	private String sunriseTime;
	//private String precipIntensityMax;
	//private String precipIntensityMaxTime;



	////////////////////////////////////////////////////////////////////////////////
	//	Constructor
	////////////////////////////////////////////////////////////////////////////////
	public DailyData(JSONObject jsonDaily)
	{
		try {
			time 						= jsonDaily.getString(WeatherConstants.TIME);
				JSONObject values = jsonDaily.getJSONObject(WeatherConstants.VALUES);

			sunsetTime 					= values.getString(WeatherConstants.SUNSET_TIME);
			sunriseTime 					= values.getString(WeatherConstants.SUNRISE_TIME);
			precipProbability 	= (float)values.getDouble(WeatherConstants.PRECIP_PROBABILITY);
			precipType 			= values.getInt(WeatherConstants.PRECIP_TYPE);;
		weatherCode			= values.getInt(WeatherConstants.WEATHER_CODE);

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


	public String getSunsetTime() {
		return sunsetTime;
	}

	public String getSunriseTime() {
		return sunriseTime;
	}






}
