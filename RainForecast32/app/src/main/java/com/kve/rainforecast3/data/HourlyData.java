package com.kve.rainforecast3.data;

import org.json.JSONException;
import org.json.JSONObject;

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
			time				= jsonHourly.getString("time");
			summary 			= jsonHourly.getString("summary");
			icon 				= jsonHourly.getString("icon");
			precipIntensity 	= jsonHourly.getString("precipIntensity");
			precipProbability 	= jsonHourly.getString("precipProbability");
			precipType 			= jsonValueFor		  ("precipType", jsonHourly);
			temperature 		= jsonHourly.getString("temperature");
			apparentTemperature = jsonHourly.getString("apparentTemperature");
			dewPoint 			= jsonHourly.getString("dewPoint");
			humidity 			= jsonHourly.getString("humidity");
			windSpeed 			= jsonHourly.getString("windSpeed");
			windBearing 		= jsonHourly.getString("windBearing");
			visibility 			= jsonValueFor		  ("visibility", jsonHourly);
			cloudCover 			= jsonValueFor		  ("cloudCover", jsonHourly);
			pressure 			= jsonHourly.getString("pressure");
			ozone 				= jsonHourly.getString("ozone");

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

	public void setPrecipIntensity(String precipIntensity) {
		this.precipIntensity = precipIntensity;
	}

	public void setPrecipProbability(String precipProbability) {
		this.precipProbability = precipProbability;
	}

	public void setPrecipType(String precipType) {
		this.precipType = precipType;
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
