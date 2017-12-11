package com.kve.ramblerswalks;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;
import android.widget.Toast;

public class ReadWalksJson {
	public ReadWalksJson()
	{
		
	}
	
	public String readWalksUrl(String walkurl){
		
		
		DefaultHttpClient httpClient = new DefaultHttpClient(new BasicHttpParams());
		HttpGet httpGet				= new HttpGet(walkurl);
		
		InputStream inputStream = null;
		String result 			= null;
		
		try {
			HttpResponse response 	= httpClient.execute(httpGet);
			HttpEntity   entity		= response.getEntity();
			inputStream 			= entity.getContent();
			BufferedReader bReader	= new BufferedReader(new InputStreamReader(inputStream, "UTF-8"), 8);
			StringBuilder sBuilder 	= new StringBuilder();
			String line				= null;
			while ((line = bReader.readLine()) != null)
			{
				sBuilder.append(line + "\n");
			}
			result 					= sBuilder.toString();
					
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result = null;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result = null;
		} catch( Exception e)  {
			e.printStackTrace();
			result = null;
		}
		finally{
			if (inputStream != null)
			{
				try {
					inputStream.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		if(null != result)
		{
		Log.i("jsonData", result);
		}
		else
		{
			Log.i("jsonData", "null");
		}
		populateWalks(result);
		return "";
	}	//		readWeatherForecast
	
	private void populateWalks(String jsonData)
	{
		if (null != jsonData)
		{

		JSONObject jsonObj;
		try {
			jsonObj = new JSONObject(jsonData);
			JSONArray  	walksData	= jsonObj.getJSONArray("features");
			for (int walkCounter = 0 ; walkCounter < walksData.length() ; walkCounter ++)
			{
				JSONObject 	walkObject 		= walksData.getJSONObject(walkCounter);
				List <Double> coords	= getCoords(walkObject.getJSONObject("geometry"));
				JSONObject properties 		= walkObject.getJSONObject("properties");
				
				WalkInfo walkInst = new WalkInfo(coords.get(0), coords.get(0), 
						properties.getString("date"), properties.getString("difficulty"), 
						properties.getString("distance_km"), properties.getString("distance_miles"), 
						properties.getString("exact"), properties.getString("group"), 
						properties.getString("id"), properties.getString("summary"), 
						properties.getString("time"), properties.getString("title"), 
						properties.getString("url"));
				RamblersMain.walks.add(walkInst);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}

	}

	private List<Double> getCoords(JSONObject geometry) {
		/*
		ArrayList <Double> coords	= null;
		try {
			coords = geometry.get("coordinates");
			for (int coordCounter = 0 ; coordCounter < jsonCoords.length() ; coordCounter ++)
			{
				coords.add(Double.ParseDouble(jsonCoords.getJSONObject(coordCounter)));
			}
				
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
		List<Double> coords	= Arrays.asList(0.0, 0.0);
		return coords;
	}
}
