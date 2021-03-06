package com.sners.snowforecast.data;

import com.sners.snowforecast.weather.WeatherConstants;
import org.json.JSONException;
import org.json.JSONObject;

public class AlertData {

	String title;
	String time;
	String expires;
	String description;
	String uri;
	
	public AlertData(JSONObject jsonAlert)
	{
		try {
			title 		= jsonAlert.getString(WeatherConstants.TITLE);
			time 		= jsonAlert.getString(WeatherConstants.TIME);
			expires 	= jsonAlert.getString(WeatherConstants.EXPIRES);
			description = jsonAlert.getString(WeatherConstants.DESCRIPTION);
			uri 		= jsonAlert.getString(WeatherConstants.URI);
	
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public String getTitle() {
		return title;
	}

	public String getTime() {
		return time;
	}

	public String getExpires() {
		return expires;
	}

	public String getDescription() {
		return description;
	}

	public String getUri() {
		return uri;
	}

	
}
