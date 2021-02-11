package com.sners.snowforecast.data;

import com.sners.snowforecast.data.IntervalData;

import org.json.JSONException;
import org.json.JSONObject;import com.sners.snowforecast.data.WeatherConstants;



public class DailyData extends IntervalData {

	private String time;
	private String icon;
	private String sunriseTime;
	private String sunsetTime;
	private String moonPhase;
	private String precipIntensityMax;
	private String precipIntensityMaxTime;

	private String dewPoint;
	private String humidity;
	private String windSpeed;
	private String windBearing;
	private String visibility;
	private String cloudCover;
	private String pressure;
	private String ozone;

	////////////////////////////////////////////////////////////////////////////////
	//	Constructor
	////////////////////////////////////////////////////////////////////////////////
	public DailyData(JSONObject jsonDaily)
	{
		try {
			time 						= jsonDaily.getString(WeatherConstants.TIME);
			summary 					= jsonDaily.getString(WeatherConstants.SUMMARY);
			sunriseTime 				= jsonDaily.getString(WeatherConstants.SUNRISE_TIME);
			sunsetTime 					= jsonDaily.getString(WeatherConstants.SUNSET_TIME);
			moonPhase 					= jsonDaily.getString(WeatherConstants.MOON_PHASE);
			precipIntensity 			= jsonDaily.getString(WeatherConstants.PRECIP_INTENSITY);
			precipIntensityMax 			= jsonDaily.getString(WeatherConstants.PRECIP_INTENSITY_MAX);
			precipIntensityMaxTime 		= jsonValueFor		 (WeatherConstants.PRECIP_INTENSITY_MAX_TIME, jsonDaily);
			precipProbability 			= jsonValueFor		 (WeatherConstants.PRECIP_PROBABILITY, 		jsonDaily);
			precipType 					= jsonValueFor		 (WeatherConstants.PRECIP_TYPE, 			jsonDaily);

			dewPoint 					= jsonDaily.getString(WeatherConstants.DEW_POINT);
			humidity 					= jsonDaily.getString(WeatherConstants.HUMIDITY);
			windSpeed 					= jsonDaily.getString(WeatherConstants.WIND_SPEED);
			windBearing 				= jsonDaily.getString(WeatherConstants.WIND_BEARING);
			visibility 					= jsonValueFor		 (WeatherConstants.VISIBILITY, 			jsonDaily);
			cloudCover 					= jsonValueFor		 (WeatherConstants.CLOUD_COVER, 			jsonDaily);
			pressure 					= jsonDaily.getString(WeatherConstants.PRESSURE);

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

	public String getSunriseTime() {
		return sunriseTime;
	}

	public String
	getSunsetTime() {
		return sunsetTime;
	}

	public String getMoonPhase() {
		return moonPhase;
	}

	public String getPrecipIntensityMax() {
		return precipIntensityMax;
	}

	public String getPrecipIntensityMaxTime() {
		return precipIntensityMaxTime;
	}


	public String getDewPoint() {
		return dewPoint;
	}

	public String getHumidity() {
		return humidity;
	}

	public String getWindSpeed() {
		return windSpeed;
	}

	public String getWindBearing() {
		return windBearing;
	}

	public String getVisibility() {
		return visibility;
	}

	public String getCloudCover() {
		return cloudCover;
	}

	public String getPressure() {
		return pressure;
	}

	public String getOzone() {
		return ozone;
	}



}
