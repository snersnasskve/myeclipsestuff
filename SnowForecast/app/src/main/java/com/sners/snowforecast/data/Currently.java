package com.sners.snowforecast.data;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class Currently {
    //  We will be calculating current values from minutely data
    private ArrayList<IntervalData> minutelyData;

    //  For displaying temperatures
    private static final DecimalFormat tempFormat = new DecimalFormat("0.0");

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
        }
    }

    ////////////////////////////////////////////////////////////////////////////////
    //	Get Summary - calculate a short phrase to show the current weather
    ////////////////////////////////////////////////////////////////////////////////
    public String getSummary() {

        Integer weatherCode = 0;
        if (minutelyData.size() > 0) {
            weatherCode = minutelyData.get(0).getWeatherCode();
        }

        return weatherHelper.summaryForWeatherCode(weatherCode);
    }

    public String getIcon() {
        return "Please implement me";
    }

    public String getTime() {
        return time;
    }

}
