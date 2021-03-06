package com.kve.rainforecast3.view;

import java.util.ArrayList;

import com.kve.rainforecast3.ForecastMainActivity;
import com.kve.rainforecast3.R;
import com.kve.rainforecast3.R.id;
import com.kve.rainforecast3.R.layout;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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

public class WeatherCurrent extends Activity {
	
	//	Please I want time until precipitation

	ImageView ivCurIcon;
	TextView tvCurSummary;
	TextView tvCurPrecipIntensity;
	TextView tvCurPrecipProbability;
	TextView tvCurTemperature;
	TextView tvCurWind;
	TextView tvCurTimeTilSunset;
	TextView tvCurTimeTilPrecip;
	TextView tvCurTimeTilSnow;
	TextView tvCurAlertString;
	HorizontalScrollView hsvCurActivityIcons;
	LinearLayout llCurIcontainer;
	LinearLayout llCurrently;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
	    setContentView(R.layout.currently);
	    ivCurIcon				= (ImageView)findViewById(R.id.ivCurIcon);
	    tvCurSummary			= (TextView) findViewById(R.id.tvCurSummary);
		tvCurPrecipIntensity	= (TextView) findViewById(R.id.tvCurPrecipIntensity);
		tvCurPrecipProbability	= (TextView) findViewById(R.id.tvCurPrecipProbability);
		tvCurTemperature		= (TextView) findViewById(R.id.tvCurTemperature);
		tvCurWind				= (TextView) findViewById(R.id.tvCurWind);
		tvCurTimeTilSunset		= (TextView) findViewById(R.id.tvCurTimeTilSunset);
		tvCurTimeTilPrecip		= (TextView) findViewById(R.id.tvCurTimeTilPrecip);
		tvCurTimeTilSnow		= (TextView) findViewById(R.id.tvCurTimeTilSnow);
		tvCurAlertString		= (TextView) findViewById(R.id.tvCurAlertString);
		hsvCurActivityIcons		= (HorizontalScrollView)findViewById(R.id.hsvCurActivityIcons);
		llCurIcontainer			= (LinearLayout) findViewById(R.id.llCurIcontainer);
		llCurrently				= (LinearLayout) findViewById(R.id.llCurrently);
		
		//Animation animExitLeft = AnimationUtils.makeInAnimation(this, true);
		//llCurrently.startAnimation(animExitLeft);


		
		tvCurSummary.			setText(ForecastMainActivity.weatherData.getHeadlineSummary());
		tvCurPrecipIntensity.	setText(ForecastMainActivity.weatherData.getCurrently().getPrecipIntensity());
		tvCurPrecipProbability.	setText(ForecastMainActivity.weatherData.getCurrently().getPrecipProbability());
		tvCurTemperature.		setText(ForecastMainActivity.weatherData.getCurrently().getTemperature());
		tvCurWind.				setText(ForecastMainActivity.weatherData.getCurrently().getWindSpeed());
		
		tvCurTimeTilSunset.		setText("" + ForecastMainActivity.weatherData.getTimeTilSunsetString());
		
		//	timeTillPrecip
		tvCurTimeTilPrecip.  	setText(ForecastMainActivity.weatherData.timeTilPrecipString(false));
		tvCurTimeTilSnow.  		setText(ForecastMainActivity.weatherData.timeTilPrecipTypeString("Snow"));

		String alertHeadline = "None";
		if (null != ForecastMainActivity.weatherData.getAlerts())
		{
			alertHeadline = ForecastMainActivity.weatherData.getAlerts().getAlertSummary();
		}
		tvCurAlertString.		setText(alertHeadline);
			
		String			iconName = ForecastMainActivity.weatherData.getHeadlineIcon().replaceAll("-", "_");
		int iconId 	= 	getResources().getIdentifier(iconName, "drawable", getPackageName());
		ivCurIcon.		setImageResource(iconId);	
		ivCurIcon.		setContentDescription(iconName);
		
		setWeatherActivityIcons();
		
		
	}


	private void setWeatherActivityIcons() {

		WeatherIconGallery iconGallery = new WeatherIconGallery();
		ArrayList <String> qualIcons = iconGallery.getWeatherActivityIcons();
		
				
		inflateWeatherActivityIcons(qualIcons);
	}



	private void inflateWeatherActivityIcons(ArrayList<String> qualIcons) {
		 LayoutInflater layoutInflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		 llCurIcontainer.removeAllViews();
		 for (String iconName : qualIcons)
		 {
			 int iconId = getResources().getIdentifier(iconName, "drawable", getPackageName());
			 View convertView = layoutInflater.inflate(R.layout.icon_gallery, null);
	       
			 final ImageView img = (ImageView) convertView.findViewById(R.id.ivIconGalleryItem);
			 img.setImageResource(iconId);
			 llCurIcontainer.addView(convertView);
		 }
        	    
	}
	
	
	
	public void displayDashboard(View v){
		Intent nextActivityIntent;
		String toastMessage;
		if (null != ForecastMainActivity.weatherData.getMinutely() && ForecastMainActivity.weatherData.getMinutely().getMaxPrecip() > 0.0f)
		{
			nextActivityIntent = new Intent(WeatherCurrent.this, MinutelyPrecipChart.class);
			toastMessage = "Show Minutely graphs";

			}
		else	if (null != ForecastMainActivity.weatherData.getHourly() && ForecastMainActivity.weatherData.getHourly().getMaxPrecip() > 0.0f)
		{
			nextActivityIntent = new Intent(WeatherCurrent.this, HourlyPrecipChart.class);
			toastMessage = "Show Hourly graphs";
		}
		else 	if (null != ForecastMainActivity.weatherData.getAlerts())
		{
			nextActivityIntent = new Intent(WeatherCurrent.this, WeatherAlert.class);
			toastMessage = "Show Alerts";
		}

		else
		{
			nextActivityIntent = new Intent(WeatherCurrent.this, WeatherDashboard.class);
			toastMessage = "Show the Dashboard";
		}
		Animation animExitLeft = AnimationUtils.makeOutAnimation(this, true);
		llCurrently.startAnimation(animExitLeft);
		Toast.makeText(getApplicationContext(), toastMessage, 
				Toast.LENGTH_SHORT).show();
		startActivity(nextActivityIntent);

		finish();

	}

	

}
