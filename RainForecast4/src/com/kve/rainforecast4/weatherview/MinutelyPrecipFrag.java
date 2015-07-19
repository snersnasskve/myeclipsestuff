package com.kve.rainforecast4.weatherview;

import java.util.ArrayList;


import android.app.Activity;
import android.os.Bundle;

import com.kve.rainforecast4.ForecastMainActivity;
import com.kve.rainforecast4.data.IntervalData;

public class MinutelyPrecipFrag extends WeatherPrecipFrag {

	Activity parentActivity;

	
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

	    precipPrefix = "Minutely Precip: ";
	    ArrayList<IntervalData> minutely = ForecastMainActivity.weatherData.getMinutelyData();
        Float maxPrecip = ForecastMainActivity.weatherData.getMinutely().getMaxPrecip();
        
        maxPrecip = setTitleAndColours(maxPrecip);
    
	    drawPrecipGraph(minutely, maxPrecip);

        drawProbabilityGraph(minutely);
	}
}
