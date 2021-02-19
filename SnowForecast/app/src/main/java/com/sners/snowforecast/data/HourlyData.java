package com.sners.snowforecast.data;

import com.sners.snowforecast.data.IntervalData;

import org.json.JSONException;
import org.json.JSONObject;
import com.sners.snowforecast.data.WeatherConstants;


public class HourlyData extends IntervalData {

	private String time;
	private String icon;
	private String temperature;
	private String pressure;

	////////////////////////////////////////////////////////////////////////////////
	//	Constructor
	////////////////////////////////////////////////////////////////////////////////
	public HourlyData(JSONObject jsonHourly)
	{
		try {
			time				= jsonHourly.getString(WeatherConstants.TIME);
			JSONObject values = jsonHourly.getJSONObject(WeatherConstants.VALUES);
			icon 				= jsonHourly.getString(WeatherConstants.ICON);
			precipIntensity 	= (float)values.getDouble(WeatherConstants.PRECIP_INTENSITY);
			precipProbability 	= (float)values.getDouble(WeatherConstants.PRECIP_PROBABILITY);
			precipType 			= values.getInt(WeatherConstants.PRECIP_TYPE);;
			temperature 		= values.getString(WeatherConstants.TEMPERATURE);
			windSpeed 			= values.getDouble(WeatherConstants.WIND_SPEED);
				pressure 			= values.getString(WeatherConstants.PRESSURE);


		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

		
	public String getTime() {
		return time;
	}

	public String getIcon() {
		return icon;
	}

		public String getTemperature() {
		return temperature;
	}



	public String getPressure() {
		return pressure;
	}


}
