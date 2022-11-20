package com.sners.snowforecast.data

import com.sners.snowforecast.weather.WeatherConstants
import org.json.JSONException
import org.json.JSONObject

/**
 * A data class of minutely weather conditions
 * It takes the raw json and converts it into a data structure
 * @param jsonMinutely The raw current data coming in from the API call
 */
class MinutelyData(jsonMinutely: JSONObject) : IntervalData() {

    /**
     * localTime Time for this minute instance
     */
    var localTime = "";

    /**
     * utcTime Time for this minute instance
     */
    var utcTime = "";

    /**
     * unixTime Time for this minute instance
     */
    var unixTime = 0L;


    /*
  Constructor
   */
    init {
        try {
            time = jsonMinutely.getString(WeatherConstants.TIME_LOCAL)

            localTime = jsonMinutely.getString(WeatherConstants.TIMESTAMP_LOCAL)
            utcTime = jsonMinutely.getString(WeatherConstants.TIMESTAMP_UTC)
            unixTime = jsonMinutely.getLong(WeatherConstants.TIMESTAMP)
            precipIntensity = jsonMinutely.getDouble(WeatherConstants.PRECIP_INTENSITY).toFloat() / 24f
            precipProbability = jsonFloatValueFor(WeatherConstants.PRECIP_TYPE_SNOW, jsonMinutely) ?: 0f
            if (precipProbability > 100) {
                precipProbability = 100f
            }
            val snow = jsonFloatValueFor(WeatherConstants.PRECIP_TYPE_SNOW, jsonMinutely) ?: 0f
            if (snow > 0) {
                weatherWords.add(WeatherConstants.PRECIP_TYPE_SNOW)
            } else if (precipIntensity > 0) {
                weatherWords.add(WeatherConstants.PRECIP_TYPE_RAIN)
            }
        } catch (e: JSONException) {
            e.printStackTrace()
        }
    }
}