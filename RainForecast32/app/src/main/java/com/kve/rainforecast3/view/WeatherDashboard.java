package com.kve.rainforecast3.view;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.kve.rainforecast3.*;

public class WeatherDashboard extends Activity {
	/*
	 * 
     *	Just about every object property in the response is optional. 
     *		In fact, a request with no data to return will return a nearly empty object, rather than a failure. 
     *		Robust code will check for the presence of required parameters before using them, and will fail gracefully if they are not present.
   	 *	All numeric properties are real numbers, except for UNIX timestamps, which are (signed) integers.
     *	Summaries on the hourly data block actually only cover up to a maximum of 24 hours, 
     *		rather than the full time period in the data block. 
     *	Summaries and icons on daily data points actually cover the period from 4AM to 4AM, 
     *		rather than the stated time period of midnight to midnight. We found that the summaries so generated were less awkward.
     *	The Forecast Data API supports HTTP compression. 
     *		We heartily recommend using it, as it will make responses much smaller over the wire. 
     *		To enable it, simply add an Accept-Encoding: gzip header to your request. 
     *		(Most HTTP client libraries wrap this functionality for you, please consult your library’s documentation for details. 
     *		Be advised that we do not support such compression over HTTP/1.0 connections.)
	 */
	
	//	Please I want time until precipitation

	ImageView ivDashSummary;
	TextView tvDashSummary;
	View vDashPrecipitation;
	TextView tvDashPrecip;
	TextView tvDashPrecipProbability;
	TextView tvDashTemperature;
	TextView tvDashWind;
	TextView tvDashTimeTilSunset;
	TextView tvDashTimeTilPrecip;
	HorizontalScrollView hsvDashActivityIcons;
	LinearLayout llDashIcontainer;
	LinearLayout llDashDashboard;
	
	//	String jsonData;

	
 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
	    setContentView(R.layout.dashboard);
	    ivDashSummary			= (ImageView)findViewById(R.id.ivDashSummary);
		tvDashSummary			= (TextView) findViewById(R.id.tvDashSummary);
	    tvDashPrecip			= (TextView) findViewById(R.id.tvDashPrecip);
	    //vDashPrecipitation	= (View) findViewById(R.id.vDashPrecipitation);
		//tvDashPrecipProbability	= (TextView) findViewById(R.id.tvDashPrecipProbability);
		tvDashTemperature		= (TextView) findViewById(R.id.tvDashTemperature);
		tvDashWind				= (TextView) findViewById(R.id.tvDashWind);
		tvDashTimeTilSunset		= (TextView) findViewById(R.id.tvDashTimeTilSunset);
		tvDashTimeTilPrecip		= (TextView) findViewById(R.id.tvDashTimeTilPrecip);
		hsvDashActivityIcons	= (HorizontalScrollView)findViewById(R.id.hsvDashActivityIcons);
		llDashIcontainer		= (LinearLayout) findViewById(R.id.llDashIcontainer);
		llDashDashboard			= (LinearLayout) findViewById(R.id.llDashboard);
		
		//Animation animExitLeft = AnimationUtils.makeInAnimation(this, false);
		//llDashDashboard.startAnimation(animExitLeft);
	
			
		//HashMap <String, String> currentInfo = ForecastMainActivity.forecastReader.parseCurrentInfo(jsonData);
		
		tvDashSummary.			setText(ForecastMainActivity.weatherData.getHeadlineSummary());
		
		tvDashTimeTilSunset.	setText("" + ForecastMainActivity.weatherData.getTimeTilSunsetString());
		
		//	timeTillPrecip
		
		tvDashTimeTilPrecip.  	setText(ForecastMainActivity.weatherData.timeTilPrecipString(false));
		
	
		String			iconName = ForecastMainActivity.weatherData.getHeadlineIcon().replaceAll("-", "_");
		int iconId 	= 	getResources().getIdentifier(iconName, "drawable", getPackageName());
		ivDashSummary.	setImageResource(iconId);	
		ivDashSummary.	setContentDescription(iconName);
		
		
		setWeatherActivityIcons();
		
		//drawPrecipitationGraph();
		
		populatePrecipitation();
		populateTemperature();
		populateWind();
		
	}


	private void setWeatherActivityIcons() {

		WeatherIconGallery iconGallery = new WeatherIconGallery();
		ArrayList <String> qualIcons = iconGallery.getWeatherActivityIcons();
		
				
		inflateWeatherActivityIcons(qualIcons);
	}


	private void inflateWeatherActivityIcons(ArrayList<String> qualIcons) {
		 LayoutInflater layoutInflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		 llDashIcontainer.removeAllViews();
		 for (String iconName : qualIcons)
		 {
			 int iconId = getResources().getIdentifier(iconName, "drawable", getPackageName());
			 View convertView = layoutInflater.inflate(R.layout.icon_gallery, null);
	       
			 final ImageView img = (ImageView) convertView.findViewById(R.id.ivIconGalleryItem);
			 img.setImageResource(iconId);
			 llDashIcontainer.addView(convertView);
		 }
        	    
	}
	
	

///////////////////////////////////////////
//	Weather Dashboard Views				 //
///////////////////////////////////////////

	/*
private void drawPrecipitationGraph()
{
	SurfaceHolder precipHolder = vDashPrecipitation.getHolder();
}
*/
	private void populatePrecipitation()
	{
		tvDashPrecip.setText(ForecastMainActivity.weatherData.getCurrently().getPrecipIntensity());
	}

	private void populateTemperature()
	{
		String tempString = ForecastMainActivity.weatherData.getCurrently().getTemperature() ;
	
		tvDashTemperature.setText(tempString);
		
		Double temperatureNum = ForecastMainActivity.weatherData.getCurrently().getTemperatureNum();
		if (temperatureNum > 20)
		{
			tvDashTemperature.setTextColor(Color.RED);
		}
		else if (temperatureNum > 10)
		{
			tvDashTemperature.setTextColor(Color.BLACK);
		}
		else if (temperatureNum < 10 && temperatureNum > 0)
		{
			tvDashTemperature.setTextColor(Color.BLUE);
		}
		else if (temperatureNum < 0)
		{
			tvDashTemperature.setTextColor(Color.GRAY);
		}
	
	}
	
	private void populateWind()
	{
		int beaufortValue = ForecastMainActivity.weatherData.getCurrently().getWindSpeedBeaufort();
		
		tvDashWind.setText(""+ beaufortValue);
		if (beaufortValue < 5)
		{
			//tvDashWind.setTextColor(Color.GREEN);
		}
		else if (beaufortValue < 7)
		{
			tvDashWind.setTextColor(Color.MAGENTA);
		}
		else
		{
			tvDashWind.setTextColor(Color.RED);
		}
		

	}
	
///////////////////////////////////////////
//Activity ending events				 //
///////////////////////////////////////////

	public void displayCurrent(View v){
		Animation animExitLeft = AnimationUtils.makeOutAnimation(this, false);
		llDashDashboard.startAnimation(animExitLeft);
		Toast.makeText(getApplicationContext(), "Show the weather", 
				Toast.LENGTH_SHORT).show();
		Intent currentIntent = new Intent(WeatherDashboard.this, WeatherCurrent.class);
		startActivity(currentIntent);
		finish();

	}


}
