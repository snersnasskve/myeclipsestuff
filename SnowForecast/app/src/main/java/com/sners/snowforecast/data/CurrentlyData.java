package com.sners.snowforecast.data;

import java.text.DecimalFormat;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class CurrentlyData extends com.sners.snowforecast.data.IntervalData {

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


	
	public CurrentlyData()
	{
		weatherHelper = new com.sners.snowforecast.data.WeatherHelper();


	}

		


	public String getIcon() {
		return icon;
	}

	public String getTemperature() {
		
		String formattedTemp = "" + tempFormat.format(getTemperatureNum() ) + WeatherConstants.DEGREES_C;
		
		return formattedTemp;
	}

	public Double getTemperatureNum() {
		Double temperatureNum = weatherHelper.fahrenheitToCelsius(temperature);	
		return temperatureNum;
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

	public int getWindSpeedBeaufort() {
		return weatherHelper.windSpeedToBeaufort(Double.parseDouble(windSpeed));
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


}
