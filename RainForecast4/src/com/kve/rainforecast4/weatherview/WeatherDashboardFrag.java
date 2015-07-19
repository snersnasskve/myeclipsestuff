package com.kve.rainforecast4.weatherview;

import java.util.ArrayList;

import com.kve.rainforecast4.ForecastMainActivity;
import com.kve.rainforecast4.R;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class WeatherDashboardFrag extends WeatherFrag {


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

 	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.dashboard, container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		ivDashSummary			= (ImageView)getActivity().findViewById(R.id.ivDashSummary);
		tvDashSummary			= (TextView) getActivity().findViewById(R.id.tvDashSummary);
		tvDashPrecip			= (TextView) getActivity().findViewById(R.id.tvDashPrecip);
		//vDashPrecipitation	= (View) findViewById(R.id.vDashPrecipitation);
		//tvDashPrecipProbability	= (TextView) findViewById(R.id.tvDashPrecipProbability);
		tvDashTemperature		= (TextView) getActivity().findViewById(R.id.tvDashTemperature);
		tvDashWind				= (TextView) getActivity().findViewById(R.id.tvDashWind);
		tvDashTimeTilSunset		= (TextView) getActivity().findViewById(R.id.tvDashTimeTilSunset);
		tvDashTimeTilPrecip		= (TextView) getActivity().findViewById(R.id.tvDashTimeTilPrecip);
		hsvDashActivityIcons	= (HorizontalScrollView)getActivity().findViewById(R.id.hsvDashActivityIcons);
		llDashIcontainer		= (LinearLayout) getActivity().findViewById(R.id.llDashIcontainer);
		llDashDashboard			= (LinearLayout) getActivity().findViewById(R.id.llDashboard);

		//Animation animExitLeft = AnimationUtils.makeInAnimation(this, false);
		//llDashDashboard.startAnimation(animExitLeft);


		//HashMap <String, String> currentInfo = ForecastMainActivity.forecastReader.parseCurrentInfo(jsonData);

		tvDashSummary.			setText(ForecastMainActivity.weatherData.getHeadlineSummary());

		tvDashTimeTilSunset.	setText("" + ForecastMainActivity.weatherData.getTimeTilSunsetString());

		//	timeTillPrecip

		tvDashTimeTilPrecip.  	setText(ForecastMainActivity.weatherData.timeTilPrecipString(false));


		String iconName = getWeatherIconName();
		ivDashSummary.	setImageResource(getWeatherIconId(iconName));	
		ivDashSummary.	setContentDescription(iconName);


		setWeatherActivityIcons();

		//drawPrecipitationGraph();

		populatePrecipitation();
		populateTemperature();
		populateWind();
	}

	

	protected void inflateWeatherActivityIcons(ArrayList<String> qualIcons) {
		llDashIcontainer.removeAllViews();
		
		for (String iconName : qualIcons)
		{
				llDashIcontainer.addView(convertViewForIcon(iconName));
		}

	}



	///////////////////////////////////////////
	//Weather Dashboard Views				 //
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



}
