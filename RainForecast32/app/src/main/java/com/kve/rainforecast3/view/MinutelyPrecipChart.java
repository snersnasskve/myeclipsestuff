package com.kve.rainforecast3.view;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.kve.rainforecast3.ForecastMainActivity;
import com.kve.rainforecast3.R;
import com.kve.rainforecast3.data.IntervalData;


public class MinutelyPrecipChart extends WeatherPrecipChart {

	
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
    
	    drawPrecipGraph(minutely, maxPrecip);

        drawProbabilityGraph(minutely);
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
