package com.sners.snowforecast.data

import com.sners.snowforecast.weather.WeatherConstants
import org.json.JSONObject
import org.json.JSONException

/**
 * A data class of hourly weather conditions
 * It takes the raw json and converts it into a data structure
 * @param jsonHourly The raw current data coming in from the API call
 */
class HourlyData(jsonHourly: JSONObject) : IntervalData() {



    /*
  Constructor
   */
    init {
        try {
            time = jsonHourly.getString(WeatherConstants.TIME)
            this.precipIntensity = readPrecipIntensityFrom(jsonHourly)
            precipProbability = jsonHourly.getDouble(WeatherConstants.PRECIP_PROBABILITY).toFloat()
            temperature = readTemperatureFrom(jsonHourly)
            windSpeed = readWindSpeedFrom(jsonHourly)
            windGusts = jsonHourly.getDouble(WeatherConstants.WIND_GUST).toFloat()
            windDir = jsonFloatValueFor(WeatherConstants.WIND_DIRECTION, jsonHourly) ?: -1f
            cloudCover = readCloudCoverFrom(jsonHourly)
            dewPoint = readDewPointFrom(jsonHourly)
            humidity = readHumidityFrom(jsonHourly)


            if (!jsonHourly.isNull(WeatherConstants.PRECIP_TYPE)) {
                //  If it is null, then it is not an array
                val weatherWordsJson = jsonHourly.getJSONArray(WeatherConstants.PRECIP_TYPE)
                for (wordCounter in 0.. weatherWordsJson.length()) {
                    val precipType = weatherWordsJson.getString(wordCounter)
                    weatherWords.add(precipType)
                }
            }
        } catch (e: JSONException) {
            // TODO Auto-generated catch block
            e.printStackTrace()
        }
    }
}