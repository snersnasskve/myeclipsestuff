package com.sners.snowforecast.data;

import java.text.DecimalFormat;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.sners.snowforecast.data.*;


/*
This class is a convenience summary.
The data it uses comes from minutely, but it calculates things once
 */
public class CurrentlyData   {

	private String headline = "Not found";
	private String icon = "";
	private float precipIntensity = 0;
	private float precipProbability = 0;
	private Integer weatherCode;
	private float temperature = 0;
	private float windSpeed = 0;	//	Wind speed is metres per second in the data classes
	private float nextRain = 0;
	private float nextSnow = 0;
	private ArrayList<String> galleryIcons;


	/*
	Constructor
	 */
	public CurrentlyData(ArrayList<IntervalData> minutely)
	{
		//	Cast the first record to a MinutelyData
		float totTemp = 0;
		float totIntens = 0;
		float totProb = 0;
		float totWind = 0;


		for (IntervalData interval :
			 minutely) {
			totTemp += interval.getTemperature();
			totIntens += interval.getPrecipIntensity();
			totProb += interval.getPrecipProbability();
			totWind += interval.getWindSpeed();
		}

		precipIntensity = totIntens / 60;
		precipProbability = totProb / 60;
		temperature = totTemp / 60;
		windSpeed = totWind / 60;

		galleryIcons = new ArrayList<String>();
	}


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

	public float getPrecipIntensity() {
		return precipIntensity;
	}

	public float getPrecipProbability() {
		return precipProbability;
	}

	public float getWindSpeed() {
		return windSpeed;
	}

	public ArrayList<String> getGalleryIcons() {
		return galleryIcons;
	}

	public void setGalleryIcons(ArrayList<String> galleryIcons) {
		this.galleryIcons = galleryIcons;
	}

	public void setWeatherCode(Integer weatherCode) {
		this.weatherCode = weatherCode;
	}
}
