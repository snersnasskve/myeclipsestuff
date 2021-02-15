package com.sners.snowforecast.data;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
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
        currentlyData = new CurrentlyData(minutely);
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
        HashMap<Integer, Integer> weatherCodes = new HashMap<Integer, Integer>();

        // Use the first 60 minutes for data - or whatever we have
        int hourCount = (minutelyData.size() > 59) ? 60 : minutelyData.size();

        for (int minCount = 0; minCount < hourCount; minCount++) {
            MinutelyData minuteInst = (MinutelyData) minutelyData.get(minCount);

            Integer temp = weatherCodes.getOrDefault(minuteInst.weatherCode, 0);
            weatherCodes.put(minuteInst.weatherCode, temp + 1);
        }
        // Now try and get the winning code
//     weatherCodes.entrySet().stream().max((entry1, entry2) ->
//             entry1.getValue() > entry2.getValue() ? 1 : -1).get().getKey();
        Integer modeCode = Collections.max(weatherCodes.entrySet(), Map.Entry.comparingByValue()).getKey();

        currentlyData.setHeadline(weatherHelper.summaryForWeatherCode(modeCode));
        currentlyData.setIcon(weatherHelper.iconForWeatherCode(modeCode));

    }

    public String getSummary() {
        return currentlyData.getSummary();
    }

    public String getIcon() {
        return currentlyData.getIcon();
    }

    public String getTime() {
        return time;
    }

    public float getPrecipIntensityNum() {
        return currentlyData.getPrecipIntensityNum();
    }

    public String getTemperature() {

        String formattedTemp = "" +
                WeatherHelper.tempFormat.format(currentlyData.getTemperature()) +
                WeatherConstants.DEGREES_C;

        return formattedTemp;
    }

}
