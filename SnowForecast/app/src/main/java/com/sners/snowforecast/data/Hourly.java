package com.sners.snowforecast.data;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;


public class Hourly {

    String summary;
    String icon;
    ArrayList<IntervalData> hourlyData;

    private Float maxPrecip;

    Set<Integer> weatherCodes = new HashSet<Integer>();
    Set<Integer> precipCodes = new HashSet<Integer>();
    WeatherHelper weatherHelper = new WeatherHelper();
    final int numPointsToPlot = 48;


    ////////////////////////////////////////////////////////////////////////////////
    //	Constructor
    ////////////////////////////////////////////////////////////////////////////////
    public Hourly(JSONArray hourlyArary) {
        maxPrecip = -1.0f;
        hourlyData = new ArrayList<IntervalData>();
        try {

            summary = "Please implement me";
            icon = "clear day";

            for (int intervalCounter = 0; intervalCounter < hourlyArary.length(); intervalCounter++) {
                HourlyData dataInst = new com.sners.snowforecast.data.HourlyData(hourlyArary.getJSONObject(intervalCounter));
                hourlyData.add(dataInst);

            }

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }


    public Float getMaxPrecip() {
        if (maxPrecip < 0.0f) {
            //	only calculate it once
            for (int hourCounter = 0 ; hourCounter < numPointsToPlot ; hourCounter ++) {

                float precip = hourlyData.get(hourCounter).getPrecipIntensity();
                if (precip > maxPrecip) {
                    maxPrecip = precip;
                }
            }
        }
        return maxPrecip;
    }

    public String getSummary() {
        return summary;
    }

    public String getIcon() {
        return icon;
    }

    public ArrayList<IntervalData> getHourlyData() {
        return hourlyData;
    }


}
