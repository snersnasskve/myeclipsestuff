package com.sners.snowforecast.data;

import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;

import org.json.JSONObject;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Currently {
    //  We will be calculating current values from minutely data
    private ArrayList<IntervalData> minutelyData;
    Date sunriseTime;
    Date sunsetTime;
    String currDateString; //   This is not in the currently date, please send it to me


    //  Used for calculating time ranges throughout

    Date currTime;


    //  A class for common weather functions
    private WeatherHelper weatherHelper;

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
    public Currently(JSONObject currentJson, String currDateString) {

        this.currDateString = currDateString;

        weatherHelper = new WeatherHelper();
        currentlyData = new CurrentlyData(currentJson);

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

        String sunriseString = String.format("%s %s", currDateString, currentlyData.getSunriseTime());
        String sunsetString = String.format("%s %s", currDateString, currentlyData.getSunsetTime());
        currTime = new Date();

        try {
            sunriseTime = dateFormat.parse(sunriseString);
            sunsetTime = dateFormat.parse(sunsetString);

        } catch (ParseException e) {
            e.printStackTrace();
            //	No date info;
        }


    }



    public String getIcon() {
        return currentlyData.getIcon();
    }

    public Date getTime() {
        return currTime;
    }

    public float getPrecipIntensityNum() {
        return currentlyData.getPrecipIntensity();
    }

    public String getTemperature() {

        String formattedTemp = String.format("%.1f%s",
               currentlyData.getTemperature(),
                WeatherConstants.DEGREES_C);

        return formattedTemp;
    }


    public String getPrecipIntensity() {
        return String.format("%.2g%s", currentlyData.getPrecipIntensity(), WeatherConstants.MM_HR);
    }

    public Float getPrecipProbability() {
         return currentlyData.getPrecipProbability();
    }

    public String getPrecipProbabilityString() {
        return weatherHelper.probabilityToPercent(getPrecipProbability());
    }


public float getWindSpeed() {
        return currentlyData.getWindSpeed();
}

    public String getHeadline() {
        return currentlyData.getHeadline();
    }

    public float getTemperatureNum() {
        return currentlyData.getTemperature();
    }

    public long getTimeTilSunset() {
        long diffInMillies = (sunsetTime.getTime() - currTime.getTime());
        long minutes = TimeUnit.MINUTES.convert(diffInMillies, TimeUnit.MILLISECONDS);
        return minutes;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////
    public boolean isDayTime() {
        boolean dayTime = true;

//  Less then 0 means the date is before the parameter
        //  Greater than 0 means the date is after the parameter
        if (currTime.compareTo(sunriseTime) < 0 ||
                currTime.compareTo(sunsetTime) > 0) {
            dayTime = false;
        }

        return dayTime;
    }



}

