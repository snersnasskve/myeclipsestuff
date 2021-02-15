package com.sners.snowforecast.data;

import java.text.DecimalFormat;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.sners.snowforecast.data.*;


public class CurrentlyData extends com.sners.snowforecast.data.IntervalData {

	private String headline = "Not found";
	private String icon = "";
	private float precipIntensity = 0;
	private float precipProbability = 0;
	private float temperature = 0;
	private float windSpeed = 0;
	private float nextRain = 0;
	private float nextSnow = 0;
	private ArrayList<String> galleryIcons;


	
	public CurrentlyData(ArrayList<IntervalData> minutely)
	{
		weatherHelper = new WeatherHelper();
		//	Cast the first record to a MinutelyData
		MinutelyData minute1 = (MinutelyData) minutely.get(0);
		precipIntensity = minute1.getPrecipIntensity();
		precipProbability = minute1.getPrecipProbability();
		temperature = minute1.getTemperature();

		galleryIcons = new ArrayList<String>();
	}

		



//	public String getTemperature() {
//
//		String formattedTemp = "" + WeatherHelper.tempFormat.format(getTemperatureNum() ) + WeatherConstants.DEGREES_C;
//
//		return formattedTemp;
//	}
//
//	public Double getTemperatureNum() {
//		Double temperatureNum = weatherHelper.fahrenheitToCelsius(temperature);
//		return temperatureNum;
//	}
//
//	public int getWindSpeedBeaufort() {
//		return weatherHelper.windSpeedToBeaufort(Double.parseDouble(windSpeed));
//	}

	public String getHeadline() {
		return headline;
	}

	public void setHeadline(String headline) {
		this.headline = headline;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public  float getTemperature() {
		return temperature;
	}

	public void setTemperature(float temperature) {
		this.temperature = temperature;
	}

	public float getPrecipitation() {
		return precipIntensity;
	}

	public void setPrecipitation(float precipitation) {
		this.precipIntensity = precipitation;
	}

	public float getWindSpeed() {
		return windSpeed;
	}

	public void setWindSpeed(float windSpeed) {
		this.windSpeed = windSpeed;
	}

	public float getNextRain() {
		return nextRain;
	}

	public void setNextRain(float nextRain) {
		this.nextRain = nextRain;
	}

	public float getNextSnow() {
		return nextSnow;
	}

	public void setNextSnow(float nextSnow) {
		this.nextSnow = nextSnow;
	}

	public ArrayList<String> getGalleryIcons() {
		return galleryIcons;
	}

	public void setGalleryIcons(ArrayList<String> galleryIcons) {
		this.galleryIcons = galleryIcons;
	}
}
