package com.sners.snowforecast.view;

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

import com.sners.snowforecast.*;
import com.sners.snowforecast.data.WeatherData;

public class WeatherDashboard extends Activity {


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
	WeatherData weatherData = ForecastMainActivity.weatherData;

	
 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
	    setContentView(R.layout.dashboard);
	    ivDashSummary			= (ImageView)findViewById(R.id.ivDashSummary);
		tvDashSummary			= (TextView) findViewById(R.id.tvDashSummary);
	    tvDashPrecip			= (TextView) findViewById(R.id.tvDashPrecip);

		tvDashTemperature		= (TextView) findViewById(R.id.tvDashTemperature);
		tvDashWind				= (TextView) findViewById(R.id.tvDashWind);
		tvDashTimeTilSunset		= (TextView) findViewById(R.id.tvDashTimeTilSunset);
		tvDashTimeTilPrecip		= (TextView) findViewById(R.id.tvDashTimeTilPrecip);
		hsvDashActivityIcons	= (HorizontalScrollView)findViewById(R.id.hsvDashActivityIcons);
		llDashIcontainer		= (LinearLayout) findViewById(R.id.llDashIcontainer);
		llDashDashboard			= (LinearLayout) findViewById(R.id.llDashboard);
		
		//Animation animExitLeft = AnimationUtils.makeInAnimation(this, false);
		//llDashDashboard.startAnimation(animExitLeft);
	
			

		tvDashSummary.			setText(weatherData.getHeadlineSummary());
		
		tvDashTimeTilSunset.	setText(
				String.format("%s", weatherData.getTimeTilSunsetString()));
		
		//	timeTillPrecip
		
		tvDashTimeTilPrecip.  	setText(weatherData.timeTilPrecipString(false));
		
	
		String			iconName = weatherData.getHeadlineIcon();
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
		String tempString = weatherData.getCurrently().getTemperature() ;
	
		tvDashTemperature.setText(tempString);
		
		float temperatureNum = weatherData.getCurrently().getTemperatureNum();
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
		float beaufortValue = weatherData.getWindSpeedBeaufort();
		
		tvDashWind.setText(String.format("%d", Math.round(beaufortValue)));
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
		Intent currentIntent = new Intent(WeatherDashboard.this,com.sners.snowforecast.view.WeatherCurrent.class);
		startActivity(currentIntent);
		finish();

	}


}
