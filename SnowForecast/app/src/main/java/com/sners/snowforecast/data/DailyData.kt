package com.sners.snowforecast.data

import org.json.JSONException
import org.json.JSONObject

/**
 * A data class of daily weather conditions
 * It takes the raw json and converts it into a data structure
 * @param jsonDaily The raw current data coming in from the API call
 */
class DailyData(jsonDaily: JSONObject) : IntervalData() {

    /**
     *  @property date Date as read from the json
     */
    var date: String? = null

    /**
     *  @property tempMin Minimum temperature in degrees C
     */
    var tempMin = 0f

    /**
     *  @property tempMax Maximum temperature in degrees C
     */
    var tempMax = 0f

    /*
  Constructor
   */
    init {
        try {
            date = jsonDaily.getString(WeatherConstants.TIME)
            precipIntensity = jsonDaily.getDouble(WeatherConstants.PRECIP_INTENSITY).toFloat()
            precipProbability = jsonDaily.getDouble(WeatherConstants.PRECIP_PROBABILITY).toFloat()
            tempMin = jsonDaily.getDouble(WeatherConstants.TEMP_MIN).toFloat()
            tempMax = jsonDaily.getDouble(WeatherConstants.TEMP_MAX).toFloat()
            if (!jsonDaily.isNull(WeatherConstants.PRECIP_TYPE)) {
                //  If it is null, then it is not an array
                val weatherWordsJson = jsonDaily.getJSONArray(WeatherConstants.PRECIP_TYPE)
                for (wordCounter in 0..weatherWordsJson.length() -1) {
                    val precipType = weatherWordsJson.getString(wordCounter)
                    weatherWords.add(precipType)
                }
            }
        } catch (e: JSONException) {
            e.printStackTrace()
        }
    }
}