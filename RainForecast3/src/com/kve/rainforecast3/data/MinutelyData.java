package com.kve.rainforecast3.data;

import org.json.JSONException;
import org.json.JSONObject;

public class MinutelyData extends IntervalData {

	String time;
	
	public MinutelyData(JSONObject jsonMinutely)
	{
		try {
			time 				= jsonMinutely.getString("time");
			summary				= "--Error--";	//	Don't ask for this field
			precipIntensity 	= jsonMinutely.getString("precipIntensity");
			precipProbability 	= jsonMinutely.getString("precipProbability");
			precipType 			= jsonValueFor		  ("precipType", jsonMinutely);
	
		} catch (JSONException e) {
			e.printStackTrace();
		}

	}

	public String getTime() {
		return time;
	}


	
	

}
