package com.sners.snowforecast;


import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;


import android.util.Log;

import com.sners.snowforecast.data.WeatherConstants;
import com.sners.snowforecast.data.WeatherData;
import com.sners.snowforecast.ForecastMainActivity;

public class ForecastReader {
	/*
	 * https://openweathermap.org
	 * https://openweathermap.org/darksky-openweather
	 * https://openweathermap.org/api
	 * My own private api key - 48fe222d3bc24e203df4da8e56196446
	 * user name is sners_nass
	 * https://api.openweathermap.org/data/2.5/onecall?lat={lat}&lon={lon}&appid={API key}
	 *  * Now going to try Climacell because they have probability
	 * 1gYIV8J35T6IfmIStb0zT0FyfxChvN8c
	 *https://www.c-sharpcorner.com/article/getting-started-with-climacell-weather-api/
	 * https://towardsdatascience.com/building-production-level-weather-visualizer-app-in-a-day-e360a68116c7
	 *
	 * c# is nearly the same as java ... maybe!
	 */

	//	sunsetTime is not allowed for 1m or 1h timesteps

	private static List<String> fields = Arrays.asList(WeatherConstants.HUMIDITY,
			WeatherConstants.TEMPERATURE, WeatherConstants.PRECIP_INTENSITY,
			WeatherConstants.PRECIP_PROBABILITY, WeatherConstants.PRECIP_TYPE,
			WeatherConstants.CLOUD_COVER,
			WeatherConstants.WEATHER_CODE, WeatherConstants.WIND_SPEED);
	//private static String fields  = "humidity,temperature,weatherCode,precipitationIntensity,
	// precipitationProbability,precipitationType,sunsetTime,cloudCover,windSpeed";
	private static String requesturl =  "";
	private static String urlTemplate ="{url}{period}?lat={lat}&lon={lon}&unit_system={unit}&start_time=now&fields={fields}&apikey={api_key}";
//	private static String urlTemplateRealtime = "https://data.climacell.co/v4/timelines?location=LAT%2CLONG&fields=FIELD_NAME&timesteps=1m,1h,1d";
	private static String urlTemplateRealtime = "https://data.climacell.co/v4/timelines?location=LAT%2CLONG&fields=FIELD_NAME&timesteps=1m";




	private static String apiKey = "1gYIV8J35T6IfmIStb0zT0FyfxChvN8c";

	//private String jsonData;
	
		public ForecastReader()
	{
		
	}
	
	public String readWeatherForecast(Double latitude, Double longitude){

		ForecastMainActivity.weatherData = null;
		//jsonData = null;
		String forecastUrl = urlTemplateRealtime;
		forecastUrl = forecastUrl.replace("LAT", "" + latitude);
		forecastUrl = forecastUrl.replace("LONG", "" + longitude);
		forecastUrl = forecastUrl.replace("FIELD_NAME", "" + fields);
		forecastUrl = forecastUrl + "&apikey=" + apiKey;



		//DefaultHttpClient httpClient = new DefaultHttpClient(new BasicHttpParams());
		//HttpGet httpGet				= new HttpGet(forecastUrl);
		
		String result 			= null;

		// New
		URL url = null;
		try {
			url = new URL(forecastUrl);
			HttpURLConnection urlConnection = null;
			try {
				urlConnection = (HttpURLConnection) url.openConnection();
				try {
					InputStream inputStream = new BufferedInputStream(urlConnection.getInputStream());

					// so this is mine below
					BufferedReader bReader	= new BufferedReader(new InputStreamReader(inputStream, "UTF-8"), 8);
					StringBuilder sBuilder 	= new StringBuilder();
					String line				= null;
					while ((line = bReader.readLine()) != null)
					{
						sBuilder.append(line + "\n");
					}
					result 					= sBuilder.toString();

				} catch (IOException e) {
					e.printStackTrace();
					result = null;
				}
			} catch (IOException e) {
				e.printStackTrace();
				result = null;
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
			result = null;
		}




		if(null != result)
		{
			Log.i("jsonData", result);
			//	Set up the data weather object
			ForecastMainActivity.weatherData = new WeatherData(result);
		}
		else
		{
			Log.i("jsonData", "null");
	}
		//jsonData = result;
		return result;
	}	//		readWeatherForecast



}

