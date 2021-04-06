package com.sners.snowforecast.data;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class HourlyData extends IntervalData {

    private String time;
    private String icon;
    private String pressure;

    ////////////////////////////////////////////////////////////////////////////////
    //	Constructor
    ////////////////////////////////////////////////////////////////////////////////
    public HourlyData(JSONObject jsonHourly) {
        try {
            time = jsonHourly.getString(WeatherConstants.TIME);
            precipIntensity = (float) jsonHourly.getDouble(WeatherConstants.PRECIP_INTENSITY);
            precipProbability = (float) jsonHourly.getDouble(WeatherConstants.PRECIP_PROBABILITY);
            temperature = (float) jsonHourly.getDouble(WeatherConstants.TEMPERATURE);
            windSpeed = jsonHourly.getDouble(WeatherConstants.WIND_SPEED);



            if (!jsonHourly.isNull(WeatherConstants.PRECIP_TYPE)) {
                //  If it is null, then it is not an array
                JSONArray weatherWordsJson = jsonHourly.getJSONArray(WeatherConstants.PRECIP_TYPE);
                for (int wordCounter = 0; wordCounter < weatherWordsJson.length(); wordCounter++) {
                    String precipType = weatherWordsJson.getString(wordCounter);
                    weatherWords.add(precipType);
                }
            }

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }


    public String getTime() {
        return time;
    }


    public float getTemperature() {
        return temperature;
    }


}
