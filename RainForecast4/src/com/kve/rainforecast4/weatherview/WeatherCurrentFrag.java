package com.kve.rainforecast4.weatherview;

import java.util.ArrayList;

import com.kve.rainforecast4.ForecastMainActivity;
import com.kve.rainforecast4.R;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class WeatherCurrentFrag extends WeatherFrag {

	Activity parentActivity;

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
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.currently, container, false);

	}

	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	    ivCurIcon				= (ImageView)getActivity().findViewById(R.id.ivCurIcon);
	    tvCurSummary			= (TextView) getActivity().findViewById(R.id.tvCurSummary);
		tvCurPrecipIntensity	= (TextView) getActivity().findViewById(R.id.tvCurPrecipIntensity);
		tvCurPrecipProbability	= (TextView) getActivity().findViewById(R.id.tvCurPrecipProbability);
		tvCurTemperature		= (TextView) getActivity().findViewById(R.id.tvCurTemperature);
		tvCurWind				= (TextView) getActivity().findViewById(R.id.tvCurWind);
		tvCurTimeTilSunset		= (TextView) getActivity().findViewById(R.id.tvCurTimeTilSunset);
		tvCurTimeTilPrecip		= (TextView) getActivity().findViewById(R.id.tvCurTimeTilPrecip);
		tvCurTimeTilSnow		= (TextView) getActivity().findViewById(R.id.tvCurTimeTilSnow);
		tvCurAlertString		= (TextView) getActivity().findViewById(R.id.tvCurAlertString);
		hsvCurActivityIcons		= (HorizontalScrollView)getActivity().findViewById(R.id.hsvCurActivityIcons);
		llCurIcontainer			= (LinearLayout) getActivity().findViewById(R.id.llCurIcontainer);
		llCurrently				= (LinearLayout) getActivity().findViewById(R.id.llCurrently);
		
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
			
		String iconName = getWeatherIconName();
		ivCurIcon.		setImageResource(getWeatherIconId(iconName));	
		ivCurIcon.		setContentDescription(iconName);
		
		setWeatherActivityIcons();
		
	}
	
	

	protected void inflateWeatherActivityIcons(ArrayList<String> qualIcons) {
		llCurIcontainer.removeAllViews();
		
		for (String iconName : qualIcons)
		{
			llCurIcontainer.addView(convertViewForIcon(iconName));
		}

	}

	
}
