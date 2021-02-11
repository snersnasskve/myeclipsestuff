package com.sners.snowforecast.data;

import java.text.DecimalFormat;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class CurrentlyData extends com.sners.snowforecast.data.IntervalData {

	private String headline = "Not found";
	private String icon = "";
	private float precipitation = 0;
	private float temperature = 0;
	private float windSpeed = 0;
	private float nextRain = 0;
	private float nextSnow = 0;
	private ArrayList<String> galleryIcons;


	
	public CurrentlyData()
	{
		weatherHelper = new com.sners.snowforecast.data.WeatherHelper();


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
		return precipitation;
	}

	public void setPrecipitation(float precipitation) {
		this.precipitation = precipitation;
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
