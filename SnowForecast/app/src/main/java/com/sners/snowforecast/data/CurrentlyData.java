package com.sners.snowforecast.data;

import java.text.DecimalFormat;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class CurrentlyData extends com.sners.snowforecast.data.IntervalData {


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

	private static final DecimalFormat tempFormat = new DecimalFormat("0.0");

	private com.sners.snowforecast.data.WeatherHelper weatherHelper;
	
	public CurrentlyData(JSONObject jsonCurrent)
	{
		weatherHelper = new com.sners.snowforecast.data.WeatherHelper();
		
		try {
			time				= jsonCurrent.getString(WeatherConstants.TIME);
			JSONObject weatherObject= jsonCurrent.getJSONArray(WeatherConstants.WEATHER).getJSONObject(0);
			summary = weatherObject.getString(WeatherConstants.SUMMARY);
			icon 				= weatherObject.getString(WeatherConstants.MAIN_WORD);
			///precipIntensity 	= jsonCurrent.getString(WeatherConstants.PRECIP_INTENSITY);

			//precipProbability 	= jsonCurrent.getString(WeatherConstants.PRECIP_PROBABILITY);
			temperature 		= jsonCurrent.getString(WeatherConstants.TEMPERATURE);
			apparentTemperature = jsonCurrent.getString(WeatherConstants.APPARENT_TEMPERATURE);
			dewPoint 			= jsonCurrent.getString(WeatherConstants.DEW_POINT);
			humidity 			= jsonCurrent.getString(WeatherConstants.HUMIDITY);
			windSpeed 			= jsonCurrent.getString(WeatherConstants.WIND_SPEED);
			windBearing 		= jsonCurrent.getString(WeatherConstants.WIND_BEARING);
			visibility 			= jsonCurrent.getString(WeatherConstants.VISIBILITY);
			cloudCover			= jsonCurrent.getString(WeatherConstants.CLOUD_COVER);
			pressure 			= jsonCurrent.getString(WeatherConstants.PRESSURE);

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

	public String getNearestStormDistance() {
		return nearestStormDistance;
	}

	public String getNearestStormBearing() {
		return nearestStormBearing;
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
