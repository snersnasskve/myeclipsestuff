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
                weatherCodes.add(dataInst.getWeatherCode());
                //	Don't add precip type unless it actually has a probability
                if (dataInst.precipProbability > 0) {
                    Integer weatherCode = dataInst.getWeatherCode();
                    weatherCodes.add(weatherCode);
                    if (dataInst.precipProbability > 0) {
                        precipCodes.add(dataInst.getPrecipType());

                        if (weatherHelper.isWeatherCodeSnowType(weatherCode)) {
                            weatherCodes.add(weatherHelper.codeForWeatherWord("Snow")); //	 for snow
                        }
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
            for (IntervalData hour : hourlyData) {
                Float precip = hour.getPrecipIntensity();
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

    public Set<Integer> getWeatherCodes() {
        return weatherCodes;
    }

    public Set<Integer> getPrecipCodes() {
        return precipCodes;
    }

    public ArrayList<IntervalData> getHourlyData() {
        return hourlyData;
    }


}
