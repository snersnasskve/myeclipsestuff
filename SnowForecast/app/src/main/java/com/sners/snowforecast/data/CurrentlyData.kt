package com.sners.snowforecast.data

import com.sners.snowforecast.weather.WeatherConstants
import org.json.JSONException
import org.json.JSONObject

/**
 * A data class of current conditions
 * It takes the raw json and converts it into a data structure
 * @param currentJson The raw current data coming in from the API call
 */
class CurrentlyData(currentJson: JSONObject) : IntervalData() {
    /**
     *  @property headline Top level summary
     */
    var headline: String = "Not found"
        private set

    /**
     *  @property icon Name of the icon that represents current conditions
     */
    var icon: String = ""
        private set


    /**
     *  @property sunsetTime Sunset time as read from the json
     */
    private var sunsetTime: String? = null
        private set

    /**
     *  @property sunriseTime Sunrise time as read from the json
     */
    private var sunriseTime: String? = null
        private set

    /*
  Constructor
   */
    init {
        try {
            /* For each property, unpack the Json string as appropriate */
            this.headline = currentJson.getString(WeatherConstants.CONDITIONS)
            this.icon = currentJson.getString(WeatherConstants.ICON)
            //  Times
            time = currentJson.getString(WeatherConstants.TIME)
            sunriseTime = currentJson.getString(WeatherConstants.SUNRISE_TIME)
            sunsetTime = currentJson.getString(WeatherConstants.SUNSET_TIME)
            //  Temperature
            temperature = readTemperatureFrom(currentJson)
            tempFeelsLike = currentJson.getDouble(WeatherConstants.TEMP_FEELS_LIKE).toFloat()
            //  Precipitation
            this.precipIntensity = currentJson.getDouble(WeatherConstants.PRECIP_INTENSITY).toFloat()
            cloudCover = readCloudCoverFrom(currentJson)

            if (this.precipIntensity > 0) {
                //	This is null if no precipIntensity
                this.precipProbability = currentJson.getDouble(WeatherConstants.PRECIP_PROBABILITY).toFloat()
            }
            if (this.precipIntensity > 0 && this.precipProbability > 0) {
                val weatherWordsJson = currentJson.getJSONArray(WeatherConstants.PRECIP_TYPE)
                for (wordCounter in 0..weatherWordsJson.length() - 1) {
                    val precipType = weatherWordsJson.getString(wordCounter)
                    weatherWords.add(precipType)
                }
            }
            //  Wind
            this.windSpeed = currentJson.getDouble(WeatherConstants.WIND_SPEED)
            this.windGusts = currentJson.getDouble(WeatherConstants.WIND_GUST).toFloat()
            val dir = currentJson.getString(WeatherConstants.WIND_DIRECTION)
            //  If winddir is not entered, it should not be initialised
            if (dir.isNotEmpty()) {
                windDir = dir.toFloat()
            }        } catch (e: JSONException) {
            e.printStackTrace()
        }
    }

    /**
     * Getters
     */
    /**
     *  Get Sunrise Time
     *  @return sunrise time as a string or empty string if not present
     */
    fun getSunriseTime() : String {
        return if (null == this.sunriseTime) {
            ""
        } else {
            sunriseTime as String
        }
    }

    /**
     *  Get Sunset Time
     *  @return sunset time as a string or empty string if not present
     */
    fun getSunsetTime() : String {
        return if (null == this.sunsetTime) {
            ""
        } else {
            sunsetTime as String
        }
    }
}

