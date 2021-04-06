package com.sners.snowforecast.data;

import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;

import com.sners.snowforecast.data.IntervalData;
import com.sners.snowforecast.data.WeatherHelper;

import java.text.ParseException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAccessor;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.sners.snowforecast.data.WeatherConstants;


public class Daily {

	private String summary;
	private String icon;
	private ArrayList <IntervalData> dailyData;
	private com.sners.snowforecast.data.DailyData today;
	String currDateString;

	Set<Integer> weatherCodes = new HashSet<Integer>();
	////////////////////////////////////////////////////////////////////////////////
	//	Constructor
	////////////////////////////////////////////////////////////////////////////////
	public Daily(JSONArray dailyArray)
	{
		dailyData		= new ArrayList <IntervalData> ();	
		today = null;
		WeatherHelper weatherHelper = new WeatherHelper();
		Date sunriseTime;
		Date sunsetTime;

		try {

			for (int intervalCounter = 0 ; intervalCounter < dailyArray.length() ; intervalCounter++)
			{
				DailyData dataInst = new DailyData(dailyArray.getJSONObject(intervalCounter));

				dailyData.add(dataInst);
//				weatherCodes.add(dataInst.getWeatherCode());
			}

	if (dailyArray.length() > 0) {
		DailyData today = (DailyData) dailyData.get(0);
		currDateString = today.getDate();
	}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (dailyData.size() > 0)
		{
			today 			= (com.sners.snowforecast.data.DailyData) dailyData.get(0);



		}
	}
//    ZonedDateTime datetime=ZonedDateTime.ofInstant(Instant.ofEpochSecond(dayValue.getLong("datetimeEpoch")), zoneId);
//

	
	public String getSummary() {
		return summary;
	}


	public String getIcon() {
		return icon;
	}

	public ArrayList <IntervalData> getDailyData() {
		return dailyData;
	}

	public Set<Integer> getWeatherCodes() {
		return weatherCodes;
	}

	public String getCurrDateString() {
		return currDateString;
	}

	public float getTempMin() {
		return today.getTempMin();
	}

	public float getTempMax() {
		return today.getTempMax();
	}
}
