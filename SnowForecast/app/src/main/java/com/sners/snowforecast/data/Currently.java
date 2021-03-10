package com.sners.snowforecast.data;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Currently {
    //  We will be calculating current values from minutely data
    private ArrayList<IntervalData> minutelyData;


    //  Used for calculating time ranges throughout

    LocalDateTime currTime;

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
        weatherHelper = new WeatherHelper();
        currentlyData = new CurrentlyData(minutely);

        if (minutelyData.size() > 0) {

            String time = (minutelyData.get(0).getTime());

            DateTimeFormatter formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME;
            TemporalAccessor parsed = formatter.parse(  time );
            currTime = LocalDateTime.from(parsed);

            generateCurrentSummaries();


        }
    }

    ////////////////////////////////////////////////////////////////////////////////
    //	Get Summary - calculate a short phrase to show the current weather
    ////////////////////////////////////////////////////////////////////////////////
    void generateCurrentSummaries() {
        HashMap<Integer, Integer> weatherCodes = new HashMap<Integer, Integer>();

        // Use the first 60 minutes for data - or whatever we have
        int hourCount = (minutelyData.size() > 59) ? 60 : minutelyData.size();
        double totWindSpeed = 0.0;

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

    public String getIcon() {
        return currentlyData.getIcon();
    }

    public LocalDateTime getTime()
    {
        return currTime;
    }

    public float getPrecipIntensityNum() {
        return currentlyData.getPrecipIntensity();
    }

    public String getTemperature() {

        String formattedTemp = String.format("%s%s",
                WeatherHelper.tempFormat.format(currentlyData.getTemperature()),
                WeatherConstants.DEGREES_C);

        return formattedTemp;
    }

    public String getPrecipIntensity() {
        return String.format("%2g%s", currentlyData.getPrecipIntensity(), WeatherConstants.MM_HR);
    }

    public String getPrecipProbability() {
        return "" + currentlyData.getPrecipProbability() + WeatherConstants.PERCENT;
    }

    public double getWindSpeedMph() {
        return (currentlyData.getWindSpeed() * WeatherConstants.MS_TO_MPH_CONVERSION);
    }
    public float getWindSpeedBeaufort() {
        return weatherHelper.windSpeedToBeaufort(getWindSpeedMph());
    }

    public String getWindSpeedBeaufortString() {
        return "" + weatherHelper.windSpeedToBeaufort(getWindSpeedMph());
    }

    public String getHeadline() {
        return currentlyData.getHeadline();
    }

    public float getTemperatureNum() {
        return currentlyData.getTemperature();
    }
}
