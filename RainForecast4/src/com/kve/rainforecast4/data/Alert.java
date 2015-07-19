package com.kve.rainforecast4.data;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Alert {
	
	ArrayList <AlertData> alertData;
	
	public Alert(JSONArray intervalData)
	{
		alertData		= new ArrayList <AlertData> ();	
		try {
	
			//JSONArray  	intervalData	= jsonAlerts.getJSONArray("data");
			for (int intervalCounter = 0 ; intervalCounter < intervalData.length() ; intervalCounter++)
			{
				AlertData dataInst = new AlertData(intervalData.getJSONObject(intervalCounter));
				alertData.add(dataInst);
			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public ArrayList <AlertData> getAlertData()
	{
		return alertData;
	}

	public String getAlertSummary()
	{
		String summary = "";
		for (int alertCounter = 0 ; alertCounter < alertData.size(); alertCounter++)
		{
			if (summary != "")
			{
				summary = summary + ", ";
			}
			String[] titleParts = alertData.get(alertCounter).getTitle().split("for");
			String headline = titleParts[0];
			summary = summary + headline;
		}
		return summary;
	}
}
