package com.kve.rainforecast4.data;

import java.text.DecimalFormat;

import org.json.JSONException;
import org.json.JSONObject;

public class CurrentlyData extends IntervalData{
	
	private String time;
	private String icon;
	private String nearestStormDistance;
	private String nearestStormBearing;
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
	
	private static final DecimalFormat tempFormat = new DecimalFormat("0.0");

	private WeatherHelper weatherHelper;
	
	public CurrentlyData(JSONObject jsonCurrent)
	{
		weatherHelper = new WeatherHelper();
		
		try {
			time				= jsonCurrent.getString("time");
			summary 			= jsonCurrent.getString("summary");
			icon 				= jsonCurrent.getString("icon");
			nearestStormDistance= jsonValueFor			("nearestStormDistance", 	jsonCurrent);
			nearestStormBearing = jsonValueFor			("nearestStormBearing", 	jsonCurrent);
			precipIntensity 	= jsonCurrent.getString("precipIntensity");
			precipProbability 	= jsonCurrent.getString("precipProbability");
			temperature 		= jsonCurrent.getString("temperature");
			apparentTemperature = jsonCurrent.getString("apparentTemperature");
			dewPoint 			= jsonCurrent.getString("dewPoint");
			humidity 			= jsonCurrent.getString("humidity");
			windSpeed 			= jsonCurrent.getString("windSpeed");
			windBearing 		= jsonCurrent.getString("windBearing");
			visibility 			= jsonValueFor			("visibility", 				jsonCurrent);
			cloudCover			= jsonCurrent.getString("cloudCover");
			pressure 			= jsonCurrent.getString("pressure");
			ozone 				= jsonCurrent.getString("ozone");
			
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
		
		String formattedTemp = "" + tempFormat.format(getTemperatureNum() ) + (char) 0x00B0 + "C";
		
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

	public String getOzone() {
		return ozone;
	}

	/*
	public void setTime(String time) {
		this.time = time;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public void setNearestStormDistance(String nearestStormDistance) {
		this.nearestStormDistance = nearestStormDistance;
	}

	public void setNearestStormBearing(String nearestStormBearing) {
		this.nearestStormBearing = nearestStormBearing;
	}

	public void setPrecipIntensity(String precipIntensity) {
		this.precipIntensity = precipIntensity;
	}

	public void setPrecipProbability(String precipProbability) {
		this.precipProbability = precipProbability;
	}

	public void setTemperature(String temperature) {
		this.temperature = temperature;
	}

	public void setApparentTemperature(String apparentTemperature) {
		this.apparentTemperature = apparentTemperature;
	}

	public void setDewPoint(String dewPoint) {
		this.dewPoint = dewPoint;
	}

	public void setHumidity(String humidity) {
		this.humidity = humidity;
	}

	public void setWindSpeed(String windSpeed) {
		this.windSpeed = windSpeed;
	}

	public void setWindBearing(String windBearing) {
		this.windBearing = windBearing;
	}

	public void setVisibility(String visibility) {
		this.visibility = visibility;
	}

	public void setCloudCover(String cloudCover) {
		this.cloudCover = cloudCover;
	}

	public void setPressure(String pressure) {
		this.pressure = pressure;
	}

	public void setOzone(String ozone) {
		this.ozone = ozone;
	}
	*/
}
