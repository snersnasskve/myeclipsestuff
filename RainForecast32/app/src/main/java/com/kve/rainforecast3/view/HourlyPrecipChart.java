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

public class HourlyPrecipChart extends WeatherPrecipChart {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
	    setContentView(R.layout.precip_chart);
	    ivPcIntensity	= (ImageView)findViewById(R.id.ivPcIntensity);
	    ivPcProbability	= (ImageView)findViewById(R.id.ivPcProbability);
	    tvPcIntensity	= (TextView) findViewById(R.id.tvPcIntensity);
	    
	    precipPrefix = "Hourly Precip: ";
	    
      ArrayList<IntervalData> hourly = ForecastMainActivity.weatherData.getHourlyData();
        Float maxPrecip = ForecastMainActivity.weatherData.getHourly().getMaxPrecip();
        
        maxPrecip = setTitleAndColours(maxPrecip);
    
	    drawPrecipGraph(hourly, maxPrecip);

        drawProbabilityGraph(hourly);
	}

	
	public void displayDashboard(View v){
		//Animation animExitLeft = AnimationUtils.makeOutAnimation(this, true);
		//llCurrently.startAnimation(animExitLeft);
		Intent nextActivityIntent;
		String toastMessage;
		if (null != ForecastMainActivity.weatherData.getAlerts())
		{
			nextActivityIntent = new Intent(HourlyPrecipChart.this, WeatherAlert.class);
			toastMessage = "Show Alerts";
		}
		else
		{
			nextActivityIntent = new Intent(HourlyPrecipChart.this, WeatherDashboard.class);
			toastMessage = "Show the Dashboard";

		}
		Animation animExitLeft = AnimationUtils.makeOutAnimation(this, true);
		Toast.makeText(getApplicationContext(), toastMessage, 
				Toast.LENGTH_SHORT).show();
		startActivity(nextActivityIntent);
		
		finish();

	}


}
