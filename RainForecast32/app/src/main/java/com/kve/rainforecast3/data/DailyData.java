package com.kve.rainforecast3.data;

import org.json.JSONException;
import org.json.JSONObject;

public class DailyData extends IntervalData {

	private String time;
	private String icon;
	private String sunriseTime;
	private String sunsetTime;
	private String moonPhase;
	private String precipIntensityMax;
	private String precipIntensityMaxTime;
	private String temperatureMin;
	private String temperatureMinTime;
	private String temperatureMax;
	private String temperatureMaxTime;
	private String apparentTemperatureMin;
	private String apparentTemperatureMinTime;
	private String apparentTemperatureMax;
	private String apparentTemperatureMaxTime;
	private String dewPoint;
	private String humidity;
	private String windSpeed;
	private String windBearing;
	private String visibility;
	private String cloudCover;
	private String pressure;
	private String ozone;
	
	public DailyData(JSONObject jsonDaily)
	{
		try {
			time 						= jsonDaily.getString("time");
			summary 					= jsonDaily.getString("summary");
			sunriseTime 				= jsonDaily.getString("sunriseTime");
			sunsetTime 					= jsonDaily.getString("sunsetTime");
			moonPhase 					= jsonDaily.getString("moonPhase");
			precipIntensity 			= jsonDaily.getString("precipIntensity");
			precipIntensityMax 			= jsonDaily.getString("precipIntensityMax");
			precipIntensityMaxTime 		= jsonValueFor		 ("precipIntensityMaxTime", jsonDaily);
			precipProbability 			= jsonValueFor		 ("precipProbability", 		jsonDaily);
			precipType 					= jsonValueFor		 ("precipType", 			jsonDaily);
			temperatureMin 				= jsonDaily.getString("temperatureMin");
			temperatureMinTime 			= jsonDaily.getString("temperatureMinTime");
			temperatureMax 				= jsonDaily.getString("temperatureMax");
			temperatureMaxTime 			= jsonDaily.getString("temperatureMaxTime");
			apparentTemperatureMin 		= jsonDaily.getString("apparentTemperatureMin");
			apparentTemperatureMinTime	= jsonDaily.getString("apparentTemperatureMinTime");
			apparentTemperatureMax 		= jsonDaily.getString("apparentTemperatureMax");
			apparentTemperatureMaxTime	= jsonDaily.getString("apparentTemperatureMaxTime");
			dewPoint 					= jsonDaily.getString("dewPoint");
			humidity 					= jsonDaily.getString("humidity");
			windSpeed 					= jsonDaily.getString("windSpeed");
			windBearing 				= jsonDaily.getString("windBearing");
			visibility 					= jsonValueFor		 ("visibility", 			jsonDaily);
			cloudCover 					= jsonValueFor		 ("cloudCover", 			jsonDaily);
			pressure 					= jsonDaily.getString("pressure");
			ozone 						= jsonDaily.getString("ozone");
	
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

	public String getSunsetTime() {
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

	public String getTemperatureMin() {
		return temperatureMin;
	}

	public String getTemperatureMinTime() {
		return temperatureMinTime;
	}

	public String getTemperatureMax() {
		return temperatureMax;
	}

	public String getTemperatureMaxTime() {
		return temperatureMaxTime;
	}

	public String getApparentTemperatureMin() {
		return apparentTemperatureMin;
	}

	public String getApparentTemperatureMinTime() {
		return apparentTemperatureMinTime;
	}

	public String getApparentTemperatureMax() {
		return apparentTemperatureMax;
	}

	public String getApparentTemperatureMaxTime() {
		return apparentTemperatureMaxTime;
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

	public void setSunriseTime(String sunriseTime) {
		this.sunriseTime = sunriseTime;
	}

	public void setSunsetTime(String sunsetTime) {
		this.sunsetTime = sunsetTime;
	}

	public void setMoonPhase(String moonPhase) {
		this.moonPhase = moonPhase;
	}

	public void setPrecipIntensity(String precipIntensity) {
		this.precipIntensity = precipIntensity;
	}

	public void setPrecipIntensityMax(String precipIntensityMax) {
		this.precipIntensityMax = precipIntensityMax;
	}

	public void setPrecipIntensityMaxTime(String precipIntensityMaxTime) {
		this.precipIntensityMaxTime = precipIntensityMaxTime;
	}

	public void setPrecipProbability(String precipProbability) {
		this.precipProbability = precipProbability;
	}

	public void setPrecipType(String precipType) {
		this.precipType = precipType;
	}

	public void setTemperatureMin(String temperatureMin) {
		this.temperatureMin = temperatureMin;
	}

	public void setTemperatureMinTime(String temperatureMinTime) {
		this.temperatureMinTime = temperatureMinTime;
	}

	public void setTemperatureMax(String temperatureMax) {
		this.temperatureMax = temperatureMax;
	}

	public void setTemperatureMaxTime(String temperatureMaxTime) {
		this.temperatureMaxTime = temperatureMaxTime;
	}

	public void setApparentTemperatureMin(String apparentTemperatureMin) {
		this.apparentTemperatureMin = apparentTemperatureMin;
	}

	public void setApparentTemperatureMinTime(String apparentTemperatureMinTime) {
		this.apparentTemperatureMinTime = apparentTemperatureMinTime;
	}

	public void setApparentTemperatureMax(String apparentTemperatureMax) {
		this.apparentTemperatureMax = apparentTemperatureMax;
	}

	public void setApparentTemperatureMaxTime(String apparentTemperatureMaxTime) {
		this.apparentTemperatureMaxTime = apparentTemperatureMaxTime;
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
