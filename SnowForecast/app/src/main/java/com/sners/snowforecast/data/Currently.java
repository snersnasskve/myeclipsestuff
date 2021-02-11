package com.sners.snowforecast.data;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class Currently {
    //  We will be calculating current values from minutely data
    private ArrayList<IntervalData> minutelyData;


    //  Used for calculating time ranges throughout
    private String time;

    //  A class for common weather functions
    private com.sners.snowforecast.data.WeatherHelper weatherHelper;

    /*
    Use the incoming data to generate the variables required:
    - Headline
    - Precipitation
    - Temperature
    - Wind
    - Time till sunset
    - Next Rain
    - Next Snow
    - Gallery icons
     */

    //  Hoping to remove this class as all our data is now sitting in minutely
    private CurrentlyData currentlyData;

    ////////////////////////////////////////////////////////////////////////////////
    //	Constructor
    ////////////////////////////////////////////////////////////////////////////////
    public Currently(ArrayList<IntervalData> minutely) {

        //  We work out current from minutely
        minutelyData = minutely;
        currentlyData = new CurrentlyData();
        weatherHelper = new com.sners.snowforecast.data.WeatherHelper();

        if (minutelyData.size() > 0) {
            time = (minutelyData.get(0).getTime());

            String weatherCode = weatherHelper.summaryForWeatherCode(
                    minutelyData.get(0).getWeatherCode());

            String icon = weatherHelper.summaryForWeatherCode(
                    minutelyData.get(0).getWeatherCode());

        }
    }

    ////////////////////////////////////////////////////////////////////////////////
    //	Get Summary - calculate a short phrase to show the current weather
    ////////////////////////////////////////////////////////////////////////////////
 void generateSummaryAndIcon() {
     HashMap<String, Integer> weatherWords = new HashMap<String, Integer>();

     minutelyData.forEach(minuteInst -> {
        String weatherSummary = weatherHelper.summaryForWeatherCode(minuteInst.weatherCode);
        Integer temp = weatherWords.getOrDefault(weatherSummary, 0);
        weatherWords.put(weatherSummary, temp + 1);
    });
     // Now try and get the winning word
 }

    public String getSummary() {
       return currentlyData.getSummary();
    }

    public String getIcon() {
        return "Please implement me";
    }

    public String getTime() {
        return time;
    }

}
