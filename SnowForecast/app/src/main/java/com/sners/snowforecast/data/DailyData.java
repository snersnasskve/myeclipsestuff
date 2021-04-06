package com.sners.snowforecast.data;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.ZoneId;
import java.util.ArrayList;


public class DailyData extends IntervalData {

    private String date;
    private String icon;


    ////////////////////////////////////////////////////////////////////////////////
    //	Constructor
    ////////////////////////////////////////////////////////////////////////////////
    public DailyData(JSONObject jsonDaily) {
        try {
            date = jsonDaily.getString(WeatherConstants.TIME);

            precipIntensity = (float) jsonDaily.getDouble(WeatherConstants.PRECIP_INTENSITY);

            precipProbability = (float) jsonDaily.getDouble(WeatherConstants.PRECIP_PROBABILITY);

            if (!jsonDaily.isNull(WeatherConstants.PRECIP_TYPE)) {
                //  If it is null, then it is not an array
                    JSONArray weatherWordsJson = jsonDaily.getJSONArray(WeatherConstants.PRECIP_TYPE);
                    for (int wordCounter = 0; wordCounter < weatherWordsJson.length(); wordCounter++) {
                        String precipType = weatherWordsJson.getString(wordCounter);
                        weatherWords.add(precipType);
                }
            }




        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


    public String getIcon() {
        return icon;
    }

    public String getDate() {
        return date;
    }


}
