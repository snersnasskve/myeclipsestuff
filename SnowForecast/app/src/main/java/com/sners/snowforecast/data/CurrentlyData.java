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
	private float tempFeelsLike = 0;
	private float windSpeed = 0;	//	Wind speed is metres per second in the data classes
	private float nextRain = 0;
	private float nextSnow = 0;
	private String time;
	private String sunsetTime;
	private String sunriseTime;
	private ArrayList<String> galleryIcons;
	private ArrayList<String> weatherWords;


	/*
	Constructor
	 */
	public CurrentlyData(JSONObject currentJson)
	{
		try {
			headline = currentJson.getString(WeatherConstants.CONDITIONS);
			icon = currentJson.getString(WeatherConstants.ICON);
			time = currentJson.getString(WeatherConstants.TIME);
			sunriseTime = currentJson.getString(WeatherConstants.SUNRISE_TIME);
			sunsetTime = currentJson.getString(WeatherConstants.SUNSET_TIME);
			precipIntensity = (float) currentJson.getDouble(WeatherConstants.PRECIP_INTENSITY);
			temperature = (float) currentJson.getDouble(WeatherConstants.TEMPERATURE);
			tempFeelsLike = (float) currentJson.getDouble(WeatherConstants.TEMP_FEELS_LIKE);
			if (precipIntensity > 0) {
				//	This is null if no precipIntensity
				precipProbability = (float) currentJson.getDouble(WeatherConstants.PRECIP_PROBABILITY);
			}

			if (precipIntensity > 0 && precipProbability > 0) {
				JSONArray weatherWordsJson = currentJson.getJSONArray(WeatherConstants.PRECIP_TYPE);
				for (int wordCounter = 0 ; wordCounter < weatherWordsJson.length() ; wordCounter++) {
					String precipType = weatherWordsJson.getString(wordCounter);
					weatherWords.add(precipType) ;
				}
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

	public String getTime() {
		return time;
	}

		public String getSunsetTime() {
		return sunsetTime;
	}

	public String getSunriseTime() {
		return sunriseTime;
	}

	public ArrayList<String> getWeatherWords() {
		return weatherWords;
	}

	public float getTempFeelsLike() {
		return tempFeelsLike;
	}
}
