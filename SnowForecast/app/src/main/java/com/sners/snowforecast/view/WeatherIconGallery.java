package com.sners.snowforecast.view;

import java.util.ArrayList;

import com.sners.snowforecast.ForecastMainActivity;
import com.sners.snowforecast.data.WeatherConstants;
import com.sners.snowforecast.data.WeatherData;
import com.sners.snowforecast.data.WeatherHelper;


public class WeatherIconGallery {

	public WeatherIconGallery()
	{
	}
	
	
	public ArrayList <String> getWeatherActivityIcons() {
		//	Call Json will it rain currently if not, add Washing icon
		ArrayList <String> qualIcons = new ArrayList <String> ();
		
		if (okToBraai())
		{
			qualIcons.add("campfire");
		}
		if (okToGoHiking())
		{
			qualIcons.add("hiking");
		}
		if (okToHangWashing())
		{
			qualIcons.add("washing");
		}
		if (okToUseUmbrella())
		{
			qualIcons.add("umbrella");
		}
		if (okToWearHat())
		{
			qualIcons.add("hat");
		}
		if (okToFlyKite())
		{
			qualIcons.add("kite");
		}
		if (okToSnow())
		{
			qualIcons.add("heavy_snow");
		}
		if (alertsFound())
		{
			qualIcons.add("alert");
		}
	
		return qualIcons;
	}
	
	///////////////////////////////////////////
	//	Weather Activity Checkers			 //
	///////////////////////////////////////////

	private boolean okToHangWashing()
	{
		boolean isItOK = false;
		if (ForecastMainActivity.weatherData.isDayTime() && 
				(ForecastMainActivity.weatherData.getTimeTilSunset() > 60) &&
				(ForecastMainActivity.weatherData.getCurrently().getTemperatureNum() > 5) &&
			(!ForecastMainActivity.weatherData.dataContainsWeatherword(
					WeatherConstants.PRECIP_TYPE_RAIN, WeatherConstants.MINUTELY
			)))
	{
			isItOK = true;
		}
		return isItOK;
	}

	private boolean okToGoHiking()
	{
		boolean isItOK = false;
		if ((ForecastMainActivity.weatherData.isDayTime()) && 
				(!ForecastMainActivity.weatherData.
						dataContainsWeatherword(WeatherConstants.PRECIP_TYPE_RAIN, WeatherConstants.HOURLY) &&
				(ForecastMainActivity.weatherData.getTimeTilSunset() > 120)))
		{
			isItOK = true;
		}
		return isItOK;
	}

	private boolean okToUseUmbrella()
	{
		boolean isItOK = false;
		if (ForecastMainActivity.weatherData.
				dataContainsWeatherword(WeatherConstants.PRECIP_TYPE_RAIN, WeatherConstants.MINUTELY))
		{
			isItOK = true;
		}
		return isItOK;
	}

	private boolean okToSnow()
	{
		boolean isItOK = false;
		if (ForecastMainActivity.weatherData.
				dataContainsWeatherword(WeatherConstants.PRECIP_TYPE_SNOW, WeatherConstants.MINUTELY) ||
				ForecastMainActivity.weatherData.
						dataContainsWeatherword(WeatherConstants.PRECIP_TYPE_SNOW, WeatherConstants.HOURLY) 	)
		{
			isItOK = true;
		}
		return isItOK;
	}

	private boolean okToBraai()
	{
		boolean isItOK = false;
		// if it is not going to rain in the next hour
		if (!ForecastMainActivity.weatherData.
				dataContainsWeatherword(WeatherConstants.PRECIP_TYPE_RAIN, WeatherConstants.MINUTELY))
		{
			isItOK = true;
		}
		return isItOK;
	}
	
	private boolean okToWearHat()
	{
		boolean isItOK = false;
		float temperature = ForecastMainActivity.weatherData.getCurrently().getTemperatureNum();
		if (ForecastMainActivity.weatherData.isDayTime() && temperature > 20)
		{
			isItOK = true;
		}
		return isItOK;
	}
	
	private boolean okToFlyKite()
	{
		boolean isItOK = false;
		float windSpeed = ForecastMainActivity.weatherData.getWind().getSpeedBeaufort();
		
		if (ForecastMainActivity.weatherData.isDayTime() && windSpeed > 3 && windSpeed < 8 &&
				(ForecastMainActivity.weatherData.getCurrently().getTemperatureNum() > 5))
		{
			isItOK = true;
		}
		return isItOK;
	}
	
	private boolean alertsFound()
	{
		boolean isItOK = false;
		if (null != ForecastMainActivity.weatherData.getAlerts())
		{
			isItOK = true;
		}
		return isItOK;
	}
	


	
	
}
