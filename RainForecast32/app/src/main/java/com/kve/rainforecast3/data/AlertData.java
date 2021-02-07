package com.kve.rainforecast3.data;

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
			title 		= jsonAlert.getString("title");
			time 		= jsonAlert.getString("time");
			expires 	= jsonAlert.getString("expires");
			description = jsonAlert.getString("description");
			uri 		= jsonAlert.getString("uri");
	
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
