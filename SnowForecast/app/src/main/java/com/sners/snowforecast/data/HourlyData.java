package com.sners.snowforecast.data;

import com.sners.snowforecast.data.IntervalData;

import org.json.JSONException;
import org.json.JSONObject;
import com.sners.snowforecast.data.WeatherConstants;


public class HourlyData extends IntervalData {

	private String time;
	private String icon;
	private String temperature;
	private String apparentTemperature;
	private String dewPoint;
	private String humidity;
	private String windSpeed;
	private String windBearing;
	private String visibility;
	private String cloudCover;
	private String pressure;
	private String ozone;

	public HourlyData(JSONObject jsonHourly)
	{
		try {
			time				= jsonHourly.getString(WeatherConstants.TIME);
			summary 			= jsonHourly.getString(WeatherConstants.SUMMARY);
			icon 				= jsonHourly.getString(WeatherConstants.ICON);
			precipIntensity 	= jsonHourly.getString(WeatherConstants.PRECIP_INTENSITY);
			precipProbability 	= jsonHourly.getString(WeatherConstants.PRECIP_PROBABILITY);
			precipType 			= jsonValueFor		  (WeatherConstants.PRECIP_TYPE, jsonHourly);
			temperature 		= jsonHourly.getString(WeatherConstants.TEMPERATURE);
			apparentTemperature = jsonHourly.getString(WeatherConstants.APPARENT_TEMPERATURE);
			dewPoint 			= jsonHourly.getString(WeatherConstants.DEW_POINT);
			humidity 			= jsonHourly.getString(WeatherConstants.HUMIDITY);
			windSpeed 			= jsonHourly.getString(WeatherConstants.WIND_SPEED);
			windBearing 		= jsonHourly.getString(WeatherConstants.WIND_BEARING);
			visibility 			= jsonValueFor		  (WeatherConstants.HOURLY, jsonHourly);
			cloudCover 			= jsonValueFor		  (WeatherConstants.CLOUD_COVER, jsonHourly);
			pressure 			= jsonHourly.getString(WeatherConstants.PRESSURE);
			ozone 				= jsonHourly.getString(WeatherConstants.OZONE);


		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

		
	public String getTime() {
		return time;
	}

	public String getIcon() {
		return icon;
	}

		public String getTemperature() {
		return temperature;
	}

	public String getApparentTemperature() {
		return apparentTemperature;
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
