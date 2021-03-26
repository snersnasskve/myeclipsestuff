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
	public CurrentlyData(JSONObject currentJson)
	{
		try {
			headline = currentJson.getString(WeatherConstants.CONDITIONS);
			icon = currentJson.getString(WeatherConstants.ICON);
			precipIntensity = (float) currentJson.getDouble(WeatherConstants.PRECIP_INTENSITY);
			if (precipIntensity > 0) {
				//	This is null if no precipIntensity
				precipProbability = (float) currentJson.getDouble(WeatherConstants.PRECIP_PROBABILITY);
			}
			windSpeed = (float) currentJson.getDouble(WeatherConstants.WIND_SPEED);
		} catch (JSONException e) {
			e.printStackTrace();
		}




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
