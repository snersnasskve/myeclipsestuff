package com.sners.snowforecast.view;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sners.snowforecast.ForecastMainActivity;
import com.sners.snowforecast.R;
import com.sners.snowforecast.data.IntervalData;
import com.sners.snowforecast.view.HourlyPrecipChart;


public class MinutelyPrecipChart extends com.sners.snowforecast.view.WeatherPrecipChart {

	final int numPointsToPlot = 60;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
	    setContentView(R.layout.precip_chart);
	    ivPcIntensity	= (ImageView)findViewById(R.id.ivPcIntensity);
	    ivPcProbability	= (ImageView)findViewById(R.id.ivPcProbability);
	    tvPcIntensity	= (TextView) findViewById(R.id.tvPcIntensity);
	    
	    precipPrefix = "Minutely Precip: ";
	    
        ArrayList<IntervalData> minutely = ForecastMainActivity.weatherData.getMinutelyData();
        Float maxPrecip = ForecastMainActivity.weatherData.getMinutely().getMaxPrecip();
        
        maxPrecip = setTitleAndColours(maxPrecip);
    
	    drawPrecipGraph(minutely, maxPrecip, numPointsToPlot);

        drawProbabilityGraph(minutely, numPointsToPlot);
	}

	public void displayDashboard(View v){
		//Animation animExitLeft = AnimationUtils.makeOutAnimation(this, true);
		//llCurrently.startAnimation(animExitLeft);
		Intent nextActivityIntent;
		String toastMessage;
		if (null != ForecastMainActivity.weatherData.getHourly() && ForecastMainActivity.weatherData.getHourly().getMaxPrecip() > 0.0f)
		{
			nextActivityIntent = new Intent(MinutelyPrecipChart.this, HourlyPrecipChart.class);
			toastMessage = "Show Hourly graphs";		}
		else
		{
			nextActivityIntent = new Intent(MinutelyPrecipChart.this, WeatherDashboard.class);
			toastMessage = "Show the Dashboard";
		}
		Animation animExitLeft = AnimationUtils.makeOutAnimation(this, true);
		Toast.makeText(getApplicationContext(), toastMessage, 
				Toast.LENGTH_SHORT).show();
		startActivity(nextActivityIntent);

		finish();

	}

}
