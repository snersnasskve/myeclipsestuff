package com.kve.rainforecast4;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;

import android.util.Log;

import com.kve.rainforecast4.data.WeatherData;

public class ForecastReader {
	/*
	 * https://api.darksky.net/forecast/a9e65eeef8627d4f2cb554e39234ee6f/37.8267,-122.4233
	 *	https://api.forecast.io/forecast/APIKEY/LATITUDE,LONGITUDE
	 *	https://api.forecast.io/forecast/APIKEY/LATITUDE,LONGITUDE,TIME
	 *	My own private api key - a9e65eeef8627d4f2cb554e39234ee6f
	 */

	private static String forecastUrlTemplate = "https://api.darksky.net/forecast/a9e65eeef8627d4f2cb554e39234ee6f/LATITUDE,LONGITUDE";


	//private String jsonData;
	
		public ForecastReader()
	{
		
	}
	
	public String readWeatherForecast(Double latitude, Double longitude){

		ForecastMainActivity.weatherData = null;
		//jsonData = null;
		String forecastUrl;
		forecastUrl = forecastUrlTemplate.replace("LATITUDE", "" + latitude);
		forecastUrl = 		  forecastUrl.replace("LONGITUDE", "" + longitude);
		
		DefaultHttpClient httpClient = new DefaultHttpClient(new BasicHttpParams());
		HttpGet httpGet				= new HttpGet(forecastUrl);
		
		InputStream inputStream = null;
		String result 			= null;
		
		try {
			HttpResponse response 	= httpClient.execute(httpGet);
			HttpEntity   entity		= response.getEntity();
			inputStream 			= entity.getContent();
			BufferedReader bReader	= new BufferedReader(new InputStreamReader(inputStream, "UTF-8"), 8);
			StringBuilder sBuilder 	= new StringBuilder();
			String line				= null;
			while ((line = bReader.readLine()) != null)
			{
				sBuilder.append(line + "\n");
			}
			result 					= sBuilder.toString();
					
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result = null;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result = null;
		} catch( Exception e)  {
			e.printStackTrace();
			result = null;
		}
		finally{
			if (inputStream != null)
			{
				try {
					inputStream.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
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
	
	
	/*
	//	Make a new object to fish out the specific info we want
	public HashMap <String, String> parseCurrentInfo(String jsonData)
	{
		if (null == currentInfo)
		{
			currentInfo = new HashMap <String, String>();

			JSONObject jsonObj;
			try {
				jsonObj = new JSONObject(jsonData);
				JSONObject jsonCurrent = jsonObj.getJSONObject("currently");
				String summary 			= jsonCurrent.getString("summary");
							if (currentInfo.containsKey("minutely"))
				{
					JSONObject jsonMinutely = jsonObj.getJSONObject("minutely");
					//	You can step down by getting JSONObjects of JSONObjects till you get to the container you need
					String minuteSummary	= jsonMinutely.getString("summary");
					currentInfo.put("minuteSummary", 	minuteSummary);
				}
				else
				{		
					currentInfo.put("minuteSummary", 	summary);
				}
				
					
			String icon 			= jsonCurrent.getString("icon");
				String precipIntensity 	= jsonCurrent.getString("precipIntensity");
				String precipProbability = jsonCurrent.getString("precipProbability");
				String temperature 		= jsonCurrent.getString("temperature");
				String windSpeed 		= jsonCurrent.getString("windSpeed");
				currentInfo.put("summary", 			summary);
				currentInfo.put("icon", 			icon);
				currentInfo.put("precipIntensity", 	precipIntensityToMils(precipIntensity));
				currentInfo.put("precipProbability",probabilityToPercent(precipProbability));
				Double temperatureNum = fahrenheitToCelsius(temperature);
				currentInfo.put("temperatureNum", 	"" + temperatureNum);
				currentInfo.put("temperature", 		"" + tempFormat.format(temperatureNum ) + (char) 0x00B0 + "C");
				currentInfo.put("windSpeedNum", 	windSpeed );
				currentInfo.put("windSpeed", 		windSpeed + " mph");
				currentInfo.put("timeTilSunset",	""+(timeTilSunset(jsonData)));
				if (isDayTime(jsonData))
				{
					currentInfo.put("isDayTime",	("true"));
				}
				else
				{
					currentInfo.put("isDayTime",	("false"));
				}


			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return currentInfo;
	}
	
	public String getCurrentInfoValue(String keyword)
	{
		return currentInfo.get(keyword);
	}
	
	
	////////////////////////////////////////////////////////////////////////////////
	//	Format Strings
	////////////////////////////////////////////////////////////////////////////////
	
	private Double fahrenheitToCelsius(String fahrenheit)
	{
		Double fahr = Double.parseDouble(fahrenheit);
		Double cels = (fahr - 32)*5/9;
		return cels ;
	}
	
	private String precipIntensityToMils(String inchesString)
	{
		Double inches = Double.parseDouble(inchesString);
		Double mils = (inches * 2.54);
		return tempFormat.format(mils) + " mm/hr";
	}

	private String probabilityToPercent(String decimalNo)
	{
		Double dec = Double.parseDouble(decimalNo);
		Double perc = (dec * 100);
		return tempFormat.format(perc) + " %";
	}
	
	
	
	///////////////////////////////////////////
	//	Parse Requests						 //
	///////////////////////////////////////////

	//	tiemKeyWord: "daily", "hourly", "minutely", "currently"
	public boolean dataContainsWeatherword(String weatherWord, String timeKeyWord, String jsonData)
	{
		boolean wordFound = false;
		//String validWords =  "daily,hourly,minutely,currently";
		ArrayList<String> validWords = new ArrayList<String>(Arrays.asList("currently", "minutely","hourly", "daily"));
		if (validWords.contains(timeKeyWord) && (!jsonData.contains(timeKeyWord)))
		{
			//	They have not supplied enough data - up the time category
			//	Find keyword in list
			int keywordPos = validWords.indexOf(timeKeyWord);
			for (int wordCounter = keywordPos ; wordCounter < validWords.size() ; wordCounter++)
			{
				if (jsonData.contains(validWords.get(wordCounter)))
				{
					timeKeyWord = validWords.get(wordCounter);
					break;
				}
			}
		}
		if (jsonData.contains(timeKeyWord))
		{
			JSONObject jsonObj;
			try {
				jsonObj = new JSONObject(jsonData);
				JSONObject jsonForInterval = jsonObj.getJSONObject(timeKeyWord);
				wordFound = (jsonForInterval.getString("summary").contains(weatherWord));
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return wordFound;
	}
	
	public boolean isDayTime(String jsonData)
	{
		boolean dayTime = true;
		JSONObject jsonObj;
		try {
			jsonObj 				= new JSONObject(jsonData);
			JSONObject 	currently 	= jsonObj.getJSONObject("currently");
			Long 		timeNowUnix = Long.parseLong(currently.getString("time"));
			JSONObject 	daily 		= jsonObj.getJSONObject("daily");
			JSONArray  	dailyData	= daily.getJSONArray("data");
			JSONObject 	dailyObject = dailyData.getJSONObject(0);
			Long 		sunriseUnix	= Long.parseLong(dailyObject.getString("sunriseTime"));
			Long 		sunsetUnix	= Long.parseLong(dailyObject.getString("sunsetTime"));

			if (timeNowUnix < sunriseUnix || timeNowUnix > sunsetUnix)
			{
				dayTime = false;
			}
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return dayTime;
	}
	
	public long timeTilSunset(String jsonData)
	{
		//	If it's night this will be negative
		long minutesTilSunset = 0;
	
		JSONObject jsonObj;
		try {
			jsonObj 				= new JSONObject(jsonData);
			JSONObject 	currently 	= jsonObj.getJSONObject("currently");
			Long 		timeNowUnix = Long.parseLong(currently.getString("time"));
			JSONObject 	daily 		= jsonObj.getJSONObject("daily");
			JSONArray  	dailyData	= daily.getJSONArray("data");
			JSONObject 	dailyObject = dailyData.getJSONObject(0);
			Long 		sunsetUnix	= Long.parseLong(dailyObject.getString("sunsetTime"));
			if (sunsetUnix > timeNowUnix)
			{
				minutesTilSunset = (sunsetUnix - timeNowUnix) / 60;
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			minutesTilSunset = -1;
		}
		
		return minutesTilSunset;
	}
	
	
	//precipIntensity: A numerical value representing the average expected intensity (in inches of liquid water per hour) 
	//	of precipitation occurring at the given time conditional on probability (that is, assuming any precipitation occurs at all). 
	//	A very rough guide is that a value of 0 in./hr. corresponds to no precipitation, 
	//	0.002 in./hr. corresponds to very light precipitation, 
	//	0.017 in./hr. corresponds to light precipitation, 
	//	0.1 in./hr. corresponds to moderate precipitation, 
	//	and 0.4 in./hr. corresponds to heavy precipitation.
	public long timeTilPrecip(String jsonData, boolean toIgnoreLightPrecip)
	{
		long minutesTilPrecip = 0;
		JSONObject jsonObj;
		
		Double minPrecip = 0.002;
		if (toIgnoreLightPrecip){
			minPrecip = 0.017;
		}

		try {
			jsonObj 				= new JSONObject(jsonData);
			JSONObject 	currently 	= jsonObj.getJSONObject("currently");
			Double precipIntensity 	= Double.parseDouble(currently.getString("precipIntensity"));
			if (precipIntensity < minPrecip)
			{
				//	Check minutely
				if (jsonData.contains("minutely"))
				{
					int rainMinutes = periodWhenValueExceeded(jsonObj, "minutely", "precipIntensity", minPrecip);
					if (rainMinutes >= 0)
					{
						minutesTilPrecip = (rainMinutes + 1);
					}
				}
				if (minutesTilPrecip == 0)
				{
					//	Check hourly
					if (jsonData.contains("hourly"))
					{
						int rainHours = periodWhenValueExceeded(jsonObj, "hourly", "precipIntensity", minPrecip);
						if (rainHours >= 0)
						{
							minutesTilPrecip = ((rainHours + 1) * 60);
						}
					}	
				}
				if (minutesTilPrecip == 0)
				{
					//	Check hourly
					if (jsonData.contains("daily"))
					{
						int rainDays = periodWhenValueExceeded(jsonObj, "daily", "precipIntensity", minPrecip);
						if (rainDays >= 0)
						{
							minutesTilPrecip = ((rainDays + 1) * 60 * 24);
						}
						else
						{
							minutesTilPrecip = -1;
						}
					}	
				}
			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return minutesTilPrecip;
	}

	
	private int periodWhenValueExceeded(JSONObject jsonDataObj, String periodKeyWord, String fieldName, double minValue)
	{
		int periodFound = -1;
		boolean precipExceedsMin = false;
		//	Check minutely
		JSONObject jsonForInterval;
		try {
			jsonForInterval = jsonDataObj.getJSONObject(periodKeyWord);
			JSONArray  	intervalData	= jsonForInterval.getJSONArray("data");

			int intervalCounter = 0;
			while (intervalCounter < intervalData.length() && !precipExceedsMin)
			{
				JSONObject 	intervalObject = intervalData.getJSONObject(intervalCounter);
				String		fieldValueString = intervalObject.getString(fieldName);
				Double 		fieldValueDbl = Double.parseDouble(fieldValueString);
				if (fieldValueDbl > minValue)
				{
					precipExceedsMin = true;
					periodFound = intervalCounter;
				}
				intervalCounter++;
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return periodFound;
	}
	*/
	



}

