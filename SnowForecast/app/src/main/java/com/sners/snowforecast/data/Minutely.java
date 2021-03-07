package com.sners.snowforecast.data;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Minutely {

    final private ArrayList<IntervalData> minutelyData;

    Set<Integer> weatherCodes = new HashSet<Integer>();
    Set<Integer> precipCodes = new HashSet<Integer>();
    WeatherHelper weatherHelper = new WeatherHelper();

    private Float maxPrecip;

    ////////////////////////////////////////////////////////////////////////////////
    //	Constructor
    ////////////////////////////////////////////////////////////////////////////////
    public Minutely(JSONArray minutelyArray) {
        maxPrecip = -1.0f;
        minutelyData = new ArrayList<IntervalData>();

        try {
            for (int intervalCounter = 0; intervalCounter < minutelyArray.length(); intervalCounter++) {
                JSONObject minuteObj = minutelyArray.getJSONObject(intervalCounter);
                com.sners.snowforecast.data.MinutelyData dataInst =
                        new com.sners.snowforecast.data.MinutelyData(minuteObj);
                minutelyData.add(dataInst);
                Integer weatherCode = dataInst.getWeatherCode();
                weatherCodes.add(weatherCode);
                if (dataInst.precipProbability > 0) {
                    precipCodes.add(dataInst.getPrecipType());

                    if (weatherHelper.isWeatherCodeSnowType(weatherCode)) {
                        weatherCodes.add(weatherHelper.codeForWeatherWord("Snow")); //	 for snow
                    }
                }
            }

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public Float getMaxPrecip() {
        if (maxPrecip < 0.0f) {
            //	only calculate it once
            for (IntervalData minute : minutelyData) {
                Float precip = minute.getPrecipIntensity();
                if (precip > maxPrecip) {
                    maxPrecip = precip;
                }
            }
        }
        return maxPrecip;
    }

    public Set<Integer> getWeatherCodes() {
        return weatherCodes;
    }

    public Set<Integer> getPrecipCodes() {
        return precipCodes;
    }

    public ArrayList<IntervalData> getMinutelyData() {
        return minutelyData;
    }


}
