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

    /*
  Constructor
   */
    init {
        try {
            time = jsonMinutely.getString(WeatherConstants.TIME_LOCAL)
            precipIntensity = jsonMinutely.getDouble(WeatherConstants.PRECIP_INTENSITY).toFloat() / 24f
            precipProbability = jsonMinutely.getDouble(WeatherConstants.PRECIP_TYPE_SNOW).toFloat()
            if (precipProbability > 100) {
                precipProbability = 100f
            }
            val snow = jsonMinutely.getDouble(WeatherConstants.PRECIP_TYPE_SNOW).toFloat()
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