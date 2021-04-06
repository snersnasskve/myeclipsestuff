package com.sners.snowforecast.data;

import com.sners.snowforecast.data.IntervalData;
import com.sners.snowforecast.data.WeatherConstants;
import com.sners.snowforecast.data.WeatherConstants;


import org.json.JSONException;
import org.json.JSONObject;

public class MinutelyData extends IntervalData {



	////////////////////////////////////////////////////////////////////////////////
	//	Constructor
	////////////////////////////////////////////////////////////////////////////////
	public MinutelyData(JSONObject jsonMinutely)
	{
		try {
			time 				= jsonMinutely.getString(WeatherConstants.TIME_LOCAL);
			precipIntensity 	= (float)jsonMinutely.getDouble(WeatherConstants.PRECIP_INTENSITY);
			precipProbability 	= (float)jsonMinutely.getDouble(WeatherConstants.PRECIP_TYPE_SNOW);
			if (precipProbability > 100) {
				precipProbability = 100;
			}


			if (precipProbability > 0) {
				float snow 	= (float)jsonMinutely.getDouble(WeatherConstants.PRECIP_TYPE_SNOW);
				if (snow > 0) {
					weatherWords.add(WeatherConstants.PRECIP_TYPE_SNOW);
				}
				else {
					weatherWords.add(WeatherConstants.PRECIP_TYPE_RAIN);
				}

			}
			//	We have a precip and a snow - there's nothing else
			//	Still need to decide what's going on that graph
			//precipType 			= jsonMinutely.getInt(WeatherConstants.PRECIP_TYPE);;
			//weatherCode			= jsonMinutely.getInt(WeatherConstants.WEATHER_CODE);


		} catch (JSONException e) {
			e.printStackTrace();
		}

	}

}
